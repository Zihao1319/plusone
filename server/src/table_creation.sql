use railway;

/*
create table user (
	user_id varchar(28) not null,
    name varchar(100) not null,
    email varchar(100) not null,
    phone varchar (50) not null,
    password varchar(120) not null,
    
    constraint user_id_pk primary key (user_id)
);

create table race (
	race_id int not null,
    name varchar (100) not null,
    
    constraint race_id_pk primary key (race_id)
);

create table horoscope (

	horoscope_id int not null auto_increment,
    name varchar (50) not null,
    
    constraint horoscope_id_pk primary key (horoscope_id)
);

create table diet (

	diet_id int not null auto_increment,
    name varchar (50) not null,
    constraint diet_id_pk primary key (diet_id)
);

create table education (

	education_id int not null auto_increment,
	name varchar(100) not null,
    
    constraint education_id_pk primary key (education_id)
);


create table profile (
	
    profile_id int,
    user_id varchar(28) not null,
    gender enum("male", "female", "everyone") not null,
	race_id int not null,
    birthyear int not null,
    age int not null,
    height int not null,
    weight int not null,
    location varchar(100) not null,
    diet_id int not null,
	horoscope_id int,
    job varchar(100),
    religion varchar (100),
    education_id int, 
    marital_status enum ("never married", "separated", "divorced", "widowed"),
    relationship_goal enum ("marriage", "serious relationship", "casual", "not sure"),
    smoking enum ("never", "sometimes", "often"),
    drinking enum("never", "often", "socially", "sober"),
    exercise enum("never", "sometimes", "often", "daily"),
    
    constraint profile_user_id_pk primary key (user_id),
    constraint profile_user_id_fk foreign key (user_id) references user (user_id),
    constraint profile_race_id_fk foreign key (race_id) references race (race_id),
    constraint profile_horoscope_fk foreign key (horoscope_id) references horoscope (horoscope_id),
    constraint profile_diet_fk foreign key (diet_id) references diet (diet_id),
    constraint profile_education_fk foreign key (education_id) references education (education_id)
);


create table friendship (

	friendship_id int not null auto_increment,
    requestor_id varchar(28) not null,
    requestee_id varchar (28) not null,
    status enum ("pending", "maybe", "matched") not null,
    
    constraint friendship_id_pk primary key (friendship_id),
    constraint requestor_user_id foreign key (requestor_id) references user (user_id),
    constraint requestee_user_id foreign key (requestee_id) references user (user_id)
);

create table preference (
	preference_id int,
    user_id varchar(28) not null,
	race_pref_id int not null,
    gender_pref enum ("male", "female", "everyone") not null,
    diet_pref_id int not null,
    minHeight int not null,
    maxHeight int not null,
    minAge int not null, 
    maxAge int not null,
    
    constraint preference_user_id_pk primary key (user_id),
    constraint preference_race_id_fk foreign key (race_pref_id) references race (race_id),
	constraint preference_diet_id_fk foreign key (diet_pref_id) references diet (diet_id)
);

create table interest (
	interest_id int not null,
    name varchar(100) not null,
    constraint interest_id_pk primary key (interest_id)
);

create table userInterest(
	id int not null,
    user_id varchar(28) not null,
    interest_id int not null,
    
	constraint user_interest_pk primary key (id),
	constraint user_interest_user_id_fk foreign key (user_id) references user (user_id),
    constraint user_interest_interest_id_fk foreign key (interest_id) references interest (interest_id)
);

create table personality (
	personality_id int not null,
    name varchar(100) not null,
    constraint personality_id_pk primary key (personality_id)
);

create table userPersonality(
	id int not null,
    user_id varchar(28) not null,
    personality_id int not null,
    
	constraint user_personality_pk primary key (id),
	constraint user_personality_user_id_fk foreign key (user_id) references user (user_id),
    constraint user_personality_personality_id_fk foreign key (personality_id) references personality (personality_id)
);


create table prompt (
	prompt_id int not null,
    name varchar(100) not null,
    constraint prompt_id_pk primary key (prompt_id)
);

create table userPrompt(
	id int not null,
    user_id varchar(28) not null,
    prompt_id int not null,
    answer LONGTEXT,
    
	constraint user_prompt_pk primary key (id),
	constraint user_prompt_user_id_fk foreign key (user_id) references user (user_id),
    constraint user_prompt_prompt_id_fk foreign key (prompt_id) references prompt (prompt_id)
);

create table subInterest (

    sub_interest_id int not null auto_increment,
	name varchar(100) not null,
    interest_id int not null,
    constraint sub_interest_id_pk primary key (sub_interest_id),
    constraint interest_id_fk foreign key (interest_id) references interest (interest_id)

);


create table userSubinterest(
	id int not null auto_increment,
    user_id varchar(28) not null,
    sub_interest_id int,
    interest_id int,
    
	constraint user_interest_pk primary key (id),
	constraint user_subinterest_user_id_fk foreign key (user_id) references user (user_id),
    constraint user_interest_subinterest_id_fk foreign key (sub_interest_id) references subInterest (sub_interest_id)

);

create table images (

	image_id int not null auto_increment,
    user_id varchar(28) not null,
    url varchar(100) not null,
    
    constraint image_id_pk primary key (image_id),
    constraint image_user_id_fk foreign key (user_id) references user (user_id)
);


create table roles (
	role_id int not null auto_increment,
    authority varchar(255),
	constraint id_pk primary key (role_id)
);

create table religion (
	religion_id int not null,
    name varchar (100) not null,
    
    constraint religion_id_pk primary key (religion_id)
);


create table language (
	language_id int not null,
    name varchar (50) not null,
    
    constraint language_id_pk primary key (language_id)
);


CREATE TABLE `userLanguage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(28) NOT NULL,
  `language_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_language_language_id_fk` (`language_id`),
  KEY `user_language_user_id_fk` (`user_id`),
  CONSTRAINT `user_language_language_id_fk` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`),
  CONSTRAINT `user_language_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
)

CREATE TABLE `chats` (
  `chat_id` bigint NOT NULL AUTO_INCREMENT,
  `user_one_id` varchar(255) DEFAULT NULL,
  `user_two_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
);

CREATE TABLE `user_role_junction` (
  `user_id` varchar(255) NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKhybpcwvq8snjhbxawo25hxous` (`role_id`),
  CONSTRAINT `FK2twda2j6bdxqpnwia4dix66qx` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKhybpcwvq8snjhbxawo25hxous` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
); 

CREATE TABLE `messages` (
  `message_id` bigint NOT NULL AUTO_INCREMENT,
  `chat_id` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `sender_id` varchar(255) DEFAULT NULL,
  `time_stamp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
);

*/