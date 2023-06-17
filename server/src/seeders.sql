use plusone;

/*
insert into interest (name)
values 
("self-improvement"),
("cooking"),
("making"),
("arts"),
("fitness"),
("games"),
("sports"),
("animals"),
("outdoor"),
("travel"),
("dance"),
("music"),
("theatre"),
("literary");

insert into subinterest (name, interest_id)
values
("Trading & investing", 1),
("Bio-hacking", 1),
("Entrepreneurship", 1),
("Seminars", 1),
("Courses", 1);


insert into subinterest (name, interest_id)
values
("Baking", 2),
("Coffee making", 2),
("Cooking", 2),
("Home brewing", 2),
("Kombucha brewing", 2),
("Wine making", 2),
("Beer making", 2),
("Others", 2),
("Candle making", 3),
("Carving", 3),
("Do It Yourself", 3),
("Lego building", 3),
("Origami", 3),
("Pottery", 3),
("Coding", 3),
("Soap making", 3),
("Jewelry making", 3),
("Crocheting", 3),
("Embroidery", 3);

insert into subinterest (name, interest_id)
values
("Calligraphy", 4),
("Coloring", 4),
("Drawing", 4),
("Flower arranging", 4),
("Painting", 4),
("Photography", 4),
("Videography", 4),
("Sketching", 4),
("Sculpting", 4),
("Museums", 4),
("Arts exhibitions", 4),
("Others", 4),
("Bodybuilding", 5),
("Brazilian jiu-jitsu", 5),
("Gymnastics", 5),
("Jogging", 5),
("Judo", 5),
("Martial arts", 5),
("Powerlifting", 5),
("Walking", 5),
("Yoga", 5),
("Zumba", 5),
("Others", 5),
("Board games", 6),
("Card games", 6),
("Poker", 6),
("Chess", 6),
("Cosplaying", 6),
("Crossword", 6),
("Laser tag", 6),
("Mahjong", 6),
("Treasure hunt", 6),
("Others", 6),
("Archery", 7),
("Badminton", 7),
("Basketball", 7),
("Bowling", 7),
("Boxing", 7),
("Cricket", 7),
("Cycling", 7),
("Fencing", 7),
("Football", 7),
("Motor sports", 7),
("Water sports", 7),
("Skating", 7),
("Swimming", 7),
("Tennis", 7),
("Table tennis", 7),
("Ultimate frisbee", 7),
("Volleyball", 7),
("Others", 7),
("Fishing", 8),
("Fishkeeping", 8),
("Pet", 8),
("Dogs", 8),
("Cats", 8),
("Others", 8),
("Backpacking", 9),
("Camping", 9),
("Climbing", 9),
("Driving", 9),
("Flying", 9),
("Hiking", 9),
("Kayaking", 9),
("Mountaineering", 9),
("Scuba diving", 9),
("Sky diving", 9),
("Gardening", 9),
("Cruise", 10),
("Excursion", 10),
("Holidaying", 10),
("Road trip", 10),
("Ballet", 11),
("Ballroom", 11),
("Break dancing", 11),
("Bollywood", 11),
("Cabaret", 11),
("Cha cha", 11),
("Freestyle", 11),
("Jazz", 11),
("Salsa", 11),
("Rock n roll", 11),
("Waltz", 11),
("Latin", 11),
("Traditional", 11),
("Others", 11),
("Alternative", 12),
("Blues", 12),
("Bollywood", 12),
("Classical", 12),
("Country", 12),
("Electro", 12),
("Jazz", 12),
("Metal", 12),
("Pop", 12),
("R&B", 12),
("Rock", 12),
("Singing", 12),
("Saxophone", 12),
("Soul", 12),
("Others", 12),
("Acting", 13),
("Drama", 13),
("Stand-up comedy", 13),
("Magic shows", 13),
("Stage shows", 13),
("Watching movies", 13),
("Others", 13),
("Astrology", 14),
("Creative writing", 14),
("Meteorology", 14),
("Language learning", 14),
("Writing", 14),
("Reading", 14);

insert into subInterest(name, interest_id)
values
("social drinking", 16),
("networking events", 16);

insert into user (user_id, name, email, phone)
values ("111111", "Fred wong", "fred@gmail.com", "94860490");

insert into user (user_id, name, email, phone)
values ("222222", "Betty Tan", "betty@gmail.com", "88901788");

insert into user (user_id, name, email, phone)
values ("333333", "John Chong", "john@gmail.com", "99891033");

insert into user (user_id, name, email, phone)
values ("444444", "Ong Swee Bee", "ong@gmail.com", "88789989");

insert into user (user_id, name, email, phone)
values ("555555", "Charles See", "charles@gmail.com", "66789876");



insert into profile (user_id, gender, race_id, birthyear, age, height, weight, location, diet_id, horoscope_id, job, religion, education_id, marital_status, relationship_goal, smoking, drinking, exercise)
values 
("222222", "female", 1, 1989, 34, 160, 55, "Singapore", 7, 1, "Admin", "Christian", 3, 1, 1, 1, 1, 2),
("333333", "male", 1, 1995, 28, 180, 77, "Singapore", 7, 3, "Manager", "Buddhist", 4, 1, 1, 3, 1, 4),
("444444", "female", 1, 1991, 32, 155, 50, "Singapore", 2, 5, "Teacher", "Free thinker", 3, 1, 2, 2, 1, 1),
("555555", "male", 1, 1992, 31, 175, 60, "Singapore", 7, 6, "Business Owner", "Buddhist", 3, 1, 2, 2, 2, 4);

insert into preference
values 
(1, "111111", 1, 3, 7, 150, 175, 25, 33),
(2, "222222", 1, 1, 7, 170, 185, 30, 40),
(3, "333333", 1, 1, 7, 170, 185, 20, 35),
(4, "444444", 3, 1, 7, 150, 185, 28, 40),
(5, "555555", 2, 1, 7, 150, 170, 28, 31);


insert into userPersonality (user_id, personality_id)
values 
("111111", 4),
("111111", 1),
("111111", 5),
("222222", 2),
("222222", 6),
("222222", 8),
("333333", 12),
("333333", 13),
("333333", 8),
("333333", 15),
("444444", 18),
("444444", 20),
("444444", 23),
("555555", 21),
("555555", 7);

insert into userLanguage (user_id, language_id)
values
("111111", 1),
("111111", 2),
("111111", 4),
("222222", 1),
("222222", 2),
("222222", 8),
("333333", 1),
("333333", 2),
("444444", 1),
("555555", 1),
("555555", 2),
("555555", 12);


insert into userSubinterest (user_id, sub_interest_id)
values 
("111111", 1),
("111111", 2),
("111111", 20),
("111111", 30),
("111111", 37),
("111111", 79),
("111111", 87),
("111111", 95),
("111111", 131),
("222222", 122),
("222222", 128),
("222222", 138),
("333333", 84),
("333333", 82),
("333333", 138),
("333333", 1),
("333333", 131),
("333333", 37),
("444444", 8),
("444444", 6),
("444444", 1),
("444444", 122),
("444444", 128),
("555555", 1),
("555555", 37),
("555555", 30),
("555555", 95),
("555555", 87),
("555555", 84);

insert into userInterest (user_id, interest_id)
values
("111111", 1),
("111111", 3),
("111111", 4),
("111111", 5),
("111111", 8),
("111111", 9),
("111111", 10),
("111111", 13),
("222222", 12),
("222222", 13),
("222222", 14),
("333333", 9),
("333333", 14),
("3333333", 1),
("3333333", 13),
("333333", 5),
("444444", 2),
("444444", 1),
("444444", 12),
("444444", 13),
("555555", 1),
("555555", 5),
("555555", 4),
("555555", 10),
("555555", 9);

insert into userPrompt (user_id, prompt_id, answer)
values
("111111", 1, "I would choose teleportation because it would allow me to instantly travel to any place in the world and experience different cultures and landscapes without the limitations of time and distance."),
("111111", 5, "I would choose to be Sherlock Holmes for a day. I would use my brilliant deductive skills to solve complex mysteries and uncover the truth behind intriguing cases, experiencing the thrill of unraveling enigmas and outsmarting adversaries."),
("111111", 13, "Are you a magician? Because whenever I look at you, everyone else disappears."),
("222222", 11, "If I could learn any skill instantly, it would be playing the guitar. I've always been fascinated by the beautiful melodies and emotional expressions that can be created through music. Being able to play the guitar would allow me to channel my creativity and connect with others on a deeper level through the universal language of music."),
("222222", 3, "If I could only eat one type of cuisine for the rest of my life, it would be Italian cuisine. I absolutely love the rich flavors, comforting pasta dishes, delectable pizzas, and indulgent desserts that Italian cuisine offers. Plus, the emphasis on fresh ingredients and simplicity in cooking make it a versatile and satisfying choice that I could enjoy day after day."),
("333333", 4, "My go-to karaoke song is Dont Stop Believin by Journey. I chose it because it's a timeless classic that never fails to get everyone singing along and feeling uplifted. The powerful lyrics and catchy melody make it a crowd favorite, and it's a song that brings back nostalgic memories and creates a fun and energetic atmosphere."),
("333333", 6, "The most unusual or unique talent I have is being able to solve a Rubik's Cube in under a minute. It's a skill that not many people possess, and I find it fascinating to manipulate the colors and patterns to solve the puzzle quickly. It always impresses others and sparks interesting conversations about problem-solving and spatial reasoning."),
("444444", 2, "The most memorable trip or adventure I've ever had was backpacking through Southeast Asia. Exploring vibrant cities, immersing myself in diverse cultures, and discovering breathtaking landscapes left a lasting impression on me. From visiting ancient temples in Cambodia to diving in crystal-clear waters in Thailand, every moment was filled with new experiences and unforgettable memories."),
("444444", 1, "I would choose the power of healing. Being able to heal others, whether it's physical ailments or emotional wounds, would bring immense joy and fulfillment. I believe in the power of compassion and helping others, and with the ability to heal, I could make a positive impact on people's lives, bringing comfort, hope, and restoration. It would be incredible to witness the transformation and happiness that comes from being able to alleviate suffering and promote well-being."),
("555555", 15, "The most spontaneous or impulsive thing I've ever done was booking a last-minute trip to a foreign country. One day, I was browsing through travel websites, and I came across an incredible flight deal. Without giving it much thought, I decided to take the plunge and booked the tickets right away. Within a few days, I found myself in a completely new and unfamiliar place, exploring its hidden gems and immersing myself in the local culture. It was an exhilarating experience, filled with unexpected adventures and unforgettable memories. Taking that spontaneous leap of faith taught me to embrace the unknown and seize opportunities when they arise."),
("555555", 10, "One of the weirdest and funniest things I've witnessed in public was seeing a squirrel steal someone's ice cream cone at a park. It happened so quickly that everyone around burst into laughter. The squirrel skillfully snatched the ice cream from the unsuspecting person's hand and scurried away, enjoying its unexpected treat.");
*/

select * from userLanguage;

select * from race;

/*
create table religion (
	religion_id int not null,
    name varchar (100) not null,
    
    constraint religion_id_pk primary key (religion_id)
);
*/

select * from race;
     