CREATE TABLE Kids (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    name            VARCHAR(20)    NOT NULL
);

CREATE TABLE Categories (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    name            VARCHAR(20)    NOT NULL
);

CREATE TABLE Contexts (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    name            VARCHAR(20)    NOT NULL
);

CREATE TABLE Contents (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    name            VARCHAR(20)    NOT NULL
);

CREATE TABLE Notifications (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    sent            VARCHAR(20)    NOT NULL,
    received        VARCHAR(20),
    content_id      INTEGER        NOT NULL,
    category_id     INTEGER,
    context_id      INTEGER,
    kid_id      INTEGER,
    FOREIGN KEY(content_id) REFERENCES Contents(ID) ON DELETE SET NULL,
    FOREIGN KEY(category_id) REFERENCES Categories(ID) ON DELETE SET NULL,
    FOREIGN KEY(context_id) REFERENCES Contexts(ID) ON DELETE SET NULL,
    FOREIGN KEY(kid_id) REFERENCES Kids(ID) ON DELETE SET NULL
);

CREATE TABLE Tags (
    ID              INTEGER        PRIMARY KEY NOT NULL,
    name            VARCHAR(20)    NOT NULL
);

CREATE TABLE Notifications_Tags (
    tag_id          INTEGER        NOT NULL,
    notification_id INTEGER        NOT NULL,
    FOREIGN KEY(tag_id) REFERENCES Tags(ID) ON DELETE CASCADE,
    FOREIGN KEY(notification_id) REFERENCES Notifications(ID) ON DELETE CASCADE
);
