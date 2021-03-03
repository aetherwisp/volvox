DELETE FROM "user_password";
DELETE FROM "user";

-- ----------------------------------------------------------------------
-- ユーザ
-- ----------------------------------------------------------------------
INSERT INTO "user" ("id", "name", "locked", "expired_at", "created_at", "created_by", "updated_at", "updated_by")
VALUES (1, 'test', false, '2030-12-31 23:59:59', CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1);

-- ----------------------------------------------------------------------
-- パスワード
-- ----------------------------------------------------------------------
INSERT INTO "user_password" ("user_id", "password", "expired_at", "created_at", "created_by", "updated_at", "updated_by")
SELECT "id", '$2a$10$ZuTTjuPG0F/YFd.UOl/sgOpE6wUcd5n.URda9GFwyyT6Zhqn1MRS2', '2030-12-31 23:59:59', CURRENT_TIMESTAMP, 1, CURRENT_TIMESTAMP, 1
  FROM "user"
 WHERE "name" = 'test';
