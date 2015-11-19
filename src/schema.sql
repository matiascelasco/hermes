CREATE TABLE Tags (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    name            VARCHAR(20)    NOT NULL
);

CREATE TABLE Notifications (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    kid_id          INT            NOT NULL,
    sent            VARCHAR(20)    NOT NULL,
    received        VARCHAR(20),
    content_id      INT            NOT NULL,
    category_id     INT,
    tag_id          INT,
    context_id      INT
);

CREATE TABLE Notifications_Tags (
    tag_id          INTEGER        NOT NULL,
    notification_id INTEGER        NOT NULL,
    FOREIGN KEY(tag_id) REFERENCES Tags(ID) ON DELETE CASCADE,
    FOREIGN KEY(notification_id) REFERENCES Notifications(ID) ON DELETE CASCADE
);