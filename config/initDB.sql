CREATE TABLE resume (
  uuid      VARCHAR(36) PRIMARY KEY NOT NULL,
  full_name TEXT                    NOT NULL
);

CREATE TABLE contact (
  id          SERIAL,
  resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  type        TEXT        NOT NULL,
  value       TEXT        NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);
DELETE FROM resume r;
SELECT * FROM resume r;
SELECT * FROM contact c;

INSERT INTO resume (uuid, full_name) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
  ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2'),
  ('dd0a70d1-5ed3-479a-b452-d5e04f21ca73', 'Name3');

INSERT INTO contact (resume_uuid, "type", value) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'PHONE', '123456'),
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'SKYPE', 'skype');


SELECT * FROM resume r
  LEFT JOIN contact c
    ON r.uuid = c.resume_uuid
ORDER BY full_name, uuid;

CREATE TABLE section (
  id           SERIAL,
  resume_uuid  VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
  section_type TEXT        NOT NULL,
  type_value   TEXT        NOT NULL,
  value        TEXT        NOT NULL
);

SELECT * FROM section order by 1,3;
SELECT * FROM contact;

DELETE FROM section;

INSERT INTO section (resume_uuid, section_type, type_value, value) VALUES
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'TextSection', 'OBJECTIVE', 'MyObjectiveInfo'),
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'TextSection', 'OBJECTIVE', 'MyObjectiveInfo'),
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'ListSection', 'ACHIEVEMENT', 'MyAchievementInfo1'),
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'ListSection', 'ACHIEVEMENT', 'MyAchievementInfo2'),
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'ListSection', 'QUALIFICATIONS', 'MyQualificationInfo1'),
  ('7de882da-02f2-4d16-8daa-60660aaf4071', 'ListSection', 'QUALIFICATIONS', 'MyQualificationInfo2');
