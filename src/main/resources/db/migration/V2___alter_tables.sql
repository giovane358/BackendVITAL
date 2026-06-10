ALTER TABLE status
    ADD active BIT DEFAULT 1;

ALTER TABLE status
    ADD created_by INT;

ALTER TABLE status
    ADD CONSTRAINT FK_STATUS_CREATED_BY
        FOREIGN KEY (created_by)
            REFERENCES usuario (id);

----
ALTER TABLE team
    ADD active BIT DEFAULT 1;

ALTER TABLE team
    ADD created_by INT;

ALTER TABLE team
    ADD CONSTRAINT FK_TEAM_CREATED_BY
        FOREIGN KEY (created_by)
            REFERENCES usuario (id);

----
ALTER TABLE occurrence
    ADD active BIT DEFAULT 1;

ALTER TABLE occurrence
    ADD created_by INT;

ALTER TABLE occurrence
    ADD CONSTRAINT FK_OCCURRENCE_CREATED_BY
        FOREIGN KEY (created_by)
            REFERENCES usuario (id);

----
ALTER TABLE team
    MODIFY available BIT DEFAULT 1;

----
