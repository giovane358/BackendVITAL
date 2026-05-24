CREATE TABLE roles(
	id			INT PRIMARY KEY IDENTITY NOT NULL,
	nome		VARCHAR(10)				 NOT NULL,
	created_at  DATETIME
);
GO

CREATE TABLE status(
	id		   INT PRIMARY KEY IDENTITY  NOT NULL,
	nome	   VARCHAR(20)				 NOT NULL,
	created_at DATETIME
);
GO



CREATE TABLE usuario(
	id 			INT PRIMARY KEY IDENTITY,
	nome		VARCHAR(90)				 NOT NULL,
	cpf			VARCHAR(14)	 UNIQUE		 NOT NULL,
	email		VARCHAR(180) UNIQUE		 NOT NULL,
	phone		VARCHAR(15)				 NOT NULL,
	senha		VARCHAR(255)			 NOT NULL,
	role_id		INT,
	status		BIT,
	created_at	DATETIME
	FOREIGN KEY (role_id) references roles(id)
);
GO

CREATE TABLE team(
	id			INT PRIMARY KEY IDENTITY,
	name		VARCHAR(15)				 NOT NULL,
	vehicle		VARCHAR(7)				 NOT NULL,
	available	FLOAT					 NOT NULL,
	current_lat FLOAT					 NOT NULL,
	current_lng FLOAT					 NOT NULL
);
GO

CREATE TABLE occurrence(
	id			INT PRIMARY KEY IDENTITY NOT NULL,
	type		VARCHAR(20)				 NOT NULL,
	description VARCHAR(255)			 NOT NULL,
	latitude	DECIMAL(9,6)	    	 NOT NULL,
	longitude	DECIMAL(9,6)			 NOT NULL,
	priority	VARCHAR(20)				 NOT NULL,
	status_id	INT						 NOT NULL,
	citizen_id  INT						 NOT NULL,
	team_id		INT						 NOT NULL,
	created_at  DATETIME,
	updated_at  DATETIME,
	FOREIGN KEY (citizen_id) REFERENCES usuario(id),
	FOREIGN KEY (team_id)	 REFERENCES team(id),
	FOREIGN KEY (status_id)	 REFERENCES status(id)
);
GO

CREATE TABLE logs(
	id			INT PRIMARY KEY IDENTITY,
	user_id		INT						 NOT NULL,
	action		VARCHAR(30)				 NOT NULL,
	description VARCHAR(255) 			 NOT NULL,
	timestamp	DATETIME,
	FOREIGN KEY (user_id) REFERENCES usuario(id)
);
GO