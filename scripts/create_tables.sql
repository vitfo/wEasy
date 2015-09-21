DROP TABLE IF EXISTS t_tracking;

DROP TABLE IF EXISTS t_comment;
DROP TABLE IF EXISTS t_image;
DROP TABLE IF EXISTS t_article;
DROP TABLE IF EXISTS t_directory;
DROP TABLE IF EXISTS t_category;
DROP TABLE IF EXISTS t_user;


CREATE TABLE t_tracking
(
  id serial NOT NULL,
  ip character varying (1000),
  url character varying (1000),
  session character varying (100),
  "time" timestamptz,

  PRIMARY KEY (id)
);

CREATE TABLE t_user
(
  id serial NOT NULL,
  username varchar(50),
  email varchar(100),
  password varchar(50),

  PRIMARY KEY (id)
);

CREATE TABLE t_category
(
  id serial NOT NULL,
  name character varying NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE t_directory
(
  id serial NOT NULL,
  category_id integer,
  name character varying(1000) NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES t_category (id)
);

CREATE TABLE t_article
(
  id serial NOT NULL,
  saved timestamptz NOT NULL,
  category_id integer,
  directory_id integer,
  header character varying(1000) NOT NULL,
  "text" text NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES t_category (id)
);

CREATE TABLE t_image
(
  id serial NOT NULL,
  directory_id integer,
  saved timestamp without time zone NOT NULL,
  name character varying(255) NOT NULL,
  data bytea,

  PRIMARY KEY (id),
  FOREIGN KEY (directory_id) REFERENCES t_directory (id)
);

CREATE TABLE t_comment
(
  id serial NOT NULL,
  "text" text NOT NULL,
  created timestamptz NOT NULL,
  article_id int NOT NULL,
  user_id int NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (article_id) REFERENCES t_article (id),
  FOREIGN KEY (user_id) REFERENCES t_user (id)
);

---------------------------------------------------------------------------
-- INSERT
---------------------------------------------------------------------------

INSERT INTO t_user (username, email, password) VALUES
  ('admin', 'admin@admin.admin', '1234'),
  ('user', 'user@user.user', 'user');

INSERT INTO t_category (name) VALUES
  ('Wicket');

INSERT INTO t_article (saved, category_id, header, text) VALUES
  (current_timestamp, 1, 'Introducing Apache Wicket', '<h1>Introducing Apache Wicket</h1><p>Invented in 2004, Wicket is one of the few survivors of the Java serverside web framework wars of the mid 2000''s. Wicket is an open source, component oriented, serverside, Java web application framework. With a history of over a decade, it is still going strong and has a solid future ahead.</p>'),
  (current_timestamp, 1, 'Documentation', '<h1>Documentation</h1><p>No matter how you want to learn about Wicket, there''s something available for you. If you want a quick reference, use the User Guide. If you rather prefer a book, there''s a couple waiting for you. And if you rather watch a video or presentation, we have that covered too. More information is <a href="http://wicket.apache.org/learn/">here</a>.</p>');

INSERT INTO t_comment (text, created, article_id, user_id) VALUES
  ('I like this article.', current_timestamp, 1, 2);