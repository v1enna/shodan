USE shodan;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM users;
INSERT INTO users(user_name, user_password, user_email) VALUES 
	('admin', '123', "antonio@shodan.it");

DELETE FROM games;
INSERT INTO games(game_name, game_image, game_price) VALUES 
	('Doom', 'Doom.png', 50),
    ('Cuphead', 'Cuphead.png', 55),
    ('Dark Souls', 'DarkSouls.png', 65),
    ('Final Fantasy VII', 'FF7.jpg', 75),
    ('Bioshock Infinite', 'BioShock.png', 25),
    ('System Shock', 'SystemShock.png', 35),
    ('Crash Bandicoot', 'Crash.jpg', 60);

DELETE FROM has_game;
INSERT INTO has_game VALUES 
	(1, 1),
    (1, 2),
    (1, 3);

DELETE FROM blog;
INSERT INTO blog(blog_title, blog_short_title, blog_html) VALUES
	("Il mio articolo", "Una breve descrizione", "Il contenuto dell'articolo"),
    ("Un altro articolo", "Una lunga lunghissima proprio lunga descrizione", "Un testo");

SELECT * FROM users;
SELECT * FROM has_game;