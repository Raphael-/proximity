CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();

CREATE TABLE LOCATION (
	ID INTEGER AUTO_INCREMENT,
	COORDINATES GEOMETRY,
	NAME VARCHAR(200),
	HITS INTEGER
);

INSERT INTO LOCATION (COORDINATES, NAME, HITS) VALUES 
('POINT(37.975627 23.735549)', 'Athens - Syntagma', 0),
('POINT(37.969377 23.740227)', 'Athens - Kallimarmaro', 0),
('POINT(37.984295 23.728136)', 'Athens - Omonoia', 0),
('POINT(37.942513 23.695080)', 'Kallithea - Stavros Niarchos Park', 0),
('POINT(37.975550 23.667306)', 'Nikaia - Village Cinemas Rentis', 0),
('POINT(37.991854 23.681760)', 'Aigaleo - Metro Station', 0),
('POINT(37.978572 23.711522)', 'Athens - Kerameikos Metro Station', 0),
('POINT(38.007262 23.656086)', 'Haidari - Lofos', 0),
('POINT(37.977106 23.650351)', 'Korydallos - Pl. Eleftherias', 0),
('POINT(37.950081 23.716887)', 'Nea Smyrni - Park', 0),
('POINT(37.928550 23.687372)', 'Palaio Faliro - Flisvos Park', 0),
('POINT(37.914809 23.705291)', 'Alimos - Marine', 0);