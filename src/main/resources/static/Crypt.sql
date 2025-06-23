
CREATE OR REPLACE PACKAGE C##SAINTRA.CRYPTO IS
  output_string   VARCHAR2(4000);     -- 암/복호화된 문자열
  encrypted_raw   RAW(2000);          -- 암호화된 RAW 데이터
  decrypted_raw   RAW(2000);          -- 복호화된 RAW 데이터
  key_string      VARCHAR2(32)
    := '22080211208021320200008021080214';
  key_bytes_raw   RAW(32)
    := UTL_RAW.CAST_TO_RAW(CONVERT(key_string, 'AL32UTF8', 'US7ASCII'));

  encryption_type PLS_INTEGER
    := DBMS_CRYPTO.ENCRYPT_AES256
     + DBMS_CRYPTO.CHAIN_CBC
     + DBMS_CRYPTO.PAD_PKCS5;

  decryption_type PLS_INTEGER
    := DBMS_CRYPTO.ENCRYPT_AES256
     + DBMS_CRYPTO.CHAIN_CBC
     + DBMS_CRYPTO.PAD_PKCS5;

  -- 암호화
  FUNCTION encrypt_aes(input_string IN VARCHAR2)
    RETURN VARCHAR2;

  -- 복호화
  FUNCTION decrypt_aes(input_string IN VARCHAR2)
    RETURN VARCHAR2;
END CRYPTO;
;
/

COMMIT;

CREATE OR REPLACE PACKAGE BODY C##SAINTRA.CRYPTO
IS

	iv_raw RAW(16) := UTL_RAW.CAST_TO_RAW('0123456789ABCDEF');

  FUNCTION ENCRYPT_AES(input_string IN  VARCHAR2)
  RETURN VARCHAR2
  IS
  BEGIN

    encrypted_raw := DBMS_CRYPTO.ENCRYPT(
      SRC => UTL_I18N.STRING_TO_RAW(input_string, 'AL32UTF8')
    , TYP => encryption_type
    , KEY => key_bytes_raw
    );

    output_string := RAWTOHEX(encrypted_raw);

    RETURN output_string;

  -- Exception
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.ENABLE;
      DBMS_OUTPUT.PUT_LINE('EXCEPTION : ' || SQLERRM);
      DBMS_OUTPUT.DISABLE;
    RETURN '__error__';
    END;
  

  FUNCTION DECRYPT_AES(input_string IN VARCHAR2)
  RETURN VARCHAR2
  IS
  BEGIN

    decrypted_raw := DBMS_CRYPTO.DECRYPT(
      SRC => HEXTORAW(input_string)
    , TYP => decryption_type
    , KEY => key_bytes_raw
    );

    output_string := CONVERT(UTL_RAW.CAST_TO_VARCHAR2(decrypted_raw), 'US7ASCII', 'AL32UTF8');
    RETURN output_string;

  -- EXCEPTION
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.ENABLE;
      DBMS_OUTPUT.PUT_LINE('EXCEPTION : ' || SQLERRM);
      DBMS_OUTPUT.DISABLE;
  RETURN '__error__';
  END;

END CRYPTO;
;
/

SELECT CRYPTO.ENCRYPT_AES ('TEST1234') FROM DUAL;

SELECT CRYPTO.DECRYPT_AES (
        '75D831A7E912C97DF4518E6A90336304'
    )
FROM DUAL;

-- 본인이 패키지를 만든 스키마로 접속한 뒤
SELECT line, position, text
FROM user_errors
WHERE
    name = 'CRYPTO'
    AND type IN ('PACKAGE', 'PACKAGE BODY')
ORDER BY sequence;

INSERT INTO 
	TB_USER(
		USERNAME,
		REALNAME,
		PASSWORD,
		EMAIL,
		ADDRESS_1,
		ADDRESS_2,
		PHONE,
		SSN
	)
	VALUES(
		'2025062302',
		'김길동',
		'1q2w3e4r!',
		'1231231@gmail.com',
		'가나다라',
		C##SAINTRA.CRYPTO.ENCRYPT_AES('고구마 밭'),
		SUBSTR('010-1234-1234', 1, 4)
            || C##SAINTRA.CRYPTO.ENCRYPT_AES(
                SUBSTR('010-1234-1234', 5)
            ),
		SUBSTR('010228-3123456', 1, 8)
		|| "C##SAINTRA".CRYPTO.ENCRYPT_AES(
			SUBSTR('010228-3123456', 9)
		)
	);

ROLLBACK;

DELETE FROM TB_USER;

SELECT * FROM TB_USER;

ALTER TABLE TB_USER MODIFY ADDRESS_2 VARCHAR2(256);

ALTER TABLE TB_USER MODIFY PHONE VARCHAR2(128);

ALTER TABLE TB_USER MODIFY PASSWORD VARCHAR2(128);

UPDATE TB_USER SET IS_ACTIVE = 'Y';

-- 잠긴 오브젝트 확인
SELECT 
  lo.session_id,
  lo.locked_mode,
  o.object_name,
  s.sid, s.serial#
FROM 
  v$locked_object lo
  JOIN all_objects o ON lo.object_id = o.object_id
  JOIN v$session s   ON lo.session_id = s.sid;