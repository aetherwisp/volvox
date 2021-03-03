-- ----------------------------------------------------------------------
-- 管理者
-- ----------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS "seq_administrator_id";
CREATE TABLE IF NOT EXISTS "administrator" (
    "id"          INTEGER       NOT NULL  DEFAULT nextval('seq_administrator_id'),
    "name"        VARCHAR(255)  NOT NULL,
    "created_at"  TIMESTAMP     NOT NULL,
    "created_by"  INTEGER       NOT NULL,
    "updated_at"  TIMESTAMP     NOT NULL,
    "updated_by"  INTEGER       NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "uq_administrator_name" UNIQUE ("name")
);
COMMENT ON COLUMN "administrator"."id" IS '【管理者 ID】管理者の識別子';
COMMENT ON COLUMN "administrator"."name" IS '【管理者名】管理者の名称';
COMMENT ON COLUMN "administrator"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "administrator"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "administrator"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "administrator"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';


-- ----------------------------------------------------------------------
-- ユーザ
-- ----------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS "seq_user_id";
CREATE TABLE IF NOT EXISTS "user" (
    "id"          INTEGER       NOT NULL  DEFAULT nextval('seq_user_id'),
    "name"        VARCHAR(255)  NOT NULL,
    "locked"      BOOLEAN       NOT NULL,
    "expired_at"  TIMESTAMP     NOT NULL,
    "created_at"  TIMESTAMP     NOT NULL,
    "created_by"  INTEGER       NOT NULL,
    "updated_at"  TIMESTAMP     NOT NULL,
    "updated_by"  INTEGER       NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "uq_user_name" UNIQUE ("name")
);
COMMENT ON COLUMN "user"."id" IS '【ユーザ ID】ユーザの識別子';
COMMENT ON COLUMN "user"."name" IS '【ユーザ名】ユーザの名称';
COMMENT ON COLUMN "user"."locked" IS '【ロック済み】TRUE：ロック中、FALSE：ロックされていない';
COMMENT ON COLUMN "user"."expired_at" IS '【有効期限】このユーザの有効期限';
COMMENT ON COLUMN "user"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "user"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "user"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "user"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';

-- ----------------------------------------------------------------------
-- ユーザパスワード
-- ----------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS "seq_user_password_id";
CREATE TABLE IF NOT EXISTS "user_password" (
    "id"          INTEGER       NOT NULL  DEFAULT nextval('seq_user_password_id'),
    "user_id"     INTEGER       NOT NULL,
    "password"    VARCHAR(255)  NOT NULL,
    "expired_at"  TIMESTAMP     NOT NULL,
    "created_at"  TIMESTAMP     NOT NULL,
    "created_by"  INTEGER       NOT NULL,
    "updated_at"  TIMESTAMP     NOT NULL,
    "updated_by"  INTEGER       NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_user_id" FOREIGN KEY ("user_id") REFERENCES "user" ("id")
);
COMMENT ON COLUMN "user_password"."id" IS '【パスワード ID】パスワードの識別子';
COMMENT ON COLUMN "user_password"."user_id" IS '【ユーザ ID】パスワードを所有するユーザの ID';
COMMENT ON COLUMN "user_password"."password" IS '【パスワード】パスワード文字列';
COMMENT ON COLUMN "user_password"."expired_at" IS '【有効期限】パスワードの有効期限';
COMMENT ON COLUMN "user_password"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "user_password"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "user_password"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "user_password"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';

-- ----------------------------------------------------------------------
-- ロール
-- ----------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS "seq_role_id";
CREATE TABLE IF NOT EXISTS "role" (
    "id"          INTEGER       NOT NULL  DEFAULT nextval('seq_role_id'),
    "name"        VARCHAR(255)  NOT NULL,
    "created_at"  TIMESTAMP     NOT NULL,
    "created_by"  INTEGER       NOT NULL,
    "updated_at"  TIMESTAMP     NOT NULL,
    "updated_by"  INTEGER       NOT NULL,
    PRIMARY KEY ("id")
);
COMMENT ON COLUMN "role"."id" IS '【ロール ID】ロールの識別子';
COMMENT ON COLUMN "role"."name" IS '【ロール名】ロールの名称';
COMMENT ON COLUMN "role"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "role"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "role"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "role"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';

-- ----------------------------------------------------------------------
-- 権限
-- ----------------------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS "seq_permission_id";
CREATE TABLE IF NOT EXISTS "permission" (
    "id"          INTEGER       NOT NULL  DEFAULT nextval('seq_permission_id'),
    "name"        VARCHAR(255)  NOT NULL,
    "created_at"  TIMESTAMP     NOT NULL,
    "created_by"  INTEGER       NOT NULL,
    "updated_at"  TIMESTAMP     NOT NULL,
    "updated_by"  INTEGER       NOT NULL,
    PRIMARY KEY ("id")
);
COMMENT ON COLUMN "permission"."id" IS '【権限 ID】権限の識別子';
COMMENT ON COLUMN "permission"."name" IS '【権限名】権限の名称';
COMMENT ON COLUMN "permission"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "permission"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "permission"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "permission"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';

-- ----------------------------------------------------------------------
-- ロール権限
-- ----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "role_permissions" (
    "role_id"       INTEGER    NOT NULL,
    "permission_id" INTEGER    NOT NULL,
    "created_at"    TIMESTAMP  NOT NULL,
    "created_by"    INTEGER    NOT NULL,
    "updated_at"    TIMESTAMP  NOT NULL,
    "updated_by"    INTEGER    NOT NULL,
    PRIMARY KEY ("role_id", "permission_id"),
    CONSTRAINT "fk_role_permissions_role" FOREIGN KEY ("role_id") REFERENCES "role" ("id"),
    CONSTRAINT "fk_role_permissions_permission" FOREIGN KEY ("permission_id") REFERENCES "permission" ("id")
);
COMMENT ON COLUMN "role_permissions"."role_id" IS '【ロール ID】ロールの識別子';
COMMENT ON COLUMN "role_permissions"."permission_id" IS '【権限 ID】権限の識別子';
COMMENT ON COLUMN "role_permissions"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "role_permissions"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "role_permissions"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "role_permissions"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';

-- ----------------------------------------------------------------------
-- ユーザロール
-- ----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "user_roles" (
    "user_id"     INTEGER    NOT NULL,
    "role_id"     INTEGER    NOT NULL,
    "created_at"  TIMESTAMP  NOT NULL,
    "created_by"  INTEGER    NOT NULL,
    "updated_at"  TIMESTAMP  NOT NULL,
    "updated_by"  INTEGER    NOT NULL,
    PRIMARY KEY ("user_id", "role_id"),
    CONSTRAINT "fk_user_roles_user" FOREIGN KEY ("user_id") REFERENCES "user" ("id"),
    CONSTRAINT "fk_user_roles_role" FOREIGN KEY ("role_id") REFERENCES "role" ("id")
);
COMMENT ON COLUMN "user_roles"."user_id" IS '【ユーザ ID】ユーザの識別子';
COMMENT ON COLUMN "user_roles"."role_id" IS '【ロール ID】ロールの識別子';
COMMENT ON COLUMN "user_roles"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "user_roles"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "user_roles"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "user_roles"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';

-- ----------------------------------------------------------------------
-- ユーザ権限
-- ----------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS "user_permissions" (
    "user_id"       INTEGER    NOT NULL,
    "permission_id" INTEGER    NOT NULL,
    "created_at"    TIMESTAMP  NOT NULL,
    "created_by"    INTEGER    NOT NULL,
    "updated_at"    TIMESTAMP  NOT NULL,
    "updated_by"    INTEGER    NOT NULL,
    PRIMARY KEY ("user_id", "permission_id"),
    CONSTRAINT "fk_user_permissions_user" FOREIGN KEY ("user_id") REFERENCES "user" ("id"),
    CONSTRAINT "fk_user_permissions_permission" FOREIGN KEY ("permission_id") REFERENCES "permission" ("id")
);
COMMENT ON COLUMN "user_permissions"."user_id" IS '【ユーザ ID】ユーザの識別子';
COMMENT ON COLUMN "user_permissions"."permission_id" IS '【権限 ID】権限の識別子';
COMMENT ON COLUMN "user_permissions"."created_at" IS '【作成日時】レコードを作成した日時';
COMMENT ON COLUMN "user_permissions"."created_by" IS '【作成者】レコードを作成した日時';
COMMENT ON COLUMN "user_permissions"."updated_at" IS '【更新日時】レコードを更新した日時';
COMMENT ON COLUMN "user_permissions"."updated_by" IS '【更新者】レコードを更新したユーザの識別子';
