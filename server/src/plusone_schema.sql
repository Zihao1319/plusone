use plusone;

/*
create table user (
	user_id varchar(28) not null,
    name varchar(100) not null,
    email varchar(100) not null,
    phone varchar (50) not null,
    
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
	
    profile_id int not null auto_increment,
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
    
    constraint profile_id_pk primary key (profile_id),
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
	preference_id int not null auto_increment,
    user_id varchar(28) not null,
	race_pref_id int not null,
    gender_pref enum ("male", "female", "everyone") not null,
    diet_pref_id int not null,
    minHeight int not null,
    maxHeight int not null,
    minAge int not null, 
    maxAge int not null,
    
    constraint preference_id_pk primary key (preference_id),
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
    
	constraint user_prompt_pk primary key (id),
	constraint user_prompt_user_id_fk foreign key (user_id) references user (user_id),
    constraint user_prompt_prompt_id_fk foreign key (prompt_id) references prompt (prompt_id)
);

*/


