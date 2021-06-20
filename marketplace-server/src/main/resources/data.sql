
INSERT INTO role (role )
VALUES
('ROLE_USER'),
('ROLE_ADMIN'),
('ROLE_COURIER'),
('ROLE_PRODUCT_MANAGER');

INSERT INTO firm(name) VALUES
('G FRESH'),
('NY MEAT'),
('BONAQUA');

INSERT INTO category(name) VALUES
('Fruits'),
('Vegetables'),
('Meat'),
('Drinks');

INSERT INTO product(name, category_id) values
('Potato', 2), --1
('Tomato', 2), --2
('Onion', 2), --3
('Ginger', 2), --4
('Cucumber', 2), --5
('Garlic', 2), --6
('Beetroot', 2), --7
('Lemon', 1), --8
('Cabbage', 2), --9
('Kiwi', 1), --10
('Safeda Mango', 1), --11
('Apple', 1), --12
('Papaya', 1), --13
('Valencia Orange', 1), --14
('Lemonade', 4), --15
('Water', 4), --16
('Strip Steak, Boneless', 3), --17
('Pork Spareribs', 3), --18
('Jalapeño Sausage', 3), --19
('Pork Ribs', 3); --20

INSERT INTO goods(prod_id, firm_id, price, unit, discount,
                  shipping_date, in_stock, status, image, description, quantity) values
(1, 1, 20, 'KILOGRAM', 50, null, true, true, null, 'Contains Vitamin C, Potassium, starch,.Potato helps in reducing inflammation, promote digestion and are good for skin.', 20),
(2, 1, 43, 'KILOGRAM', 10, null, true, true, null, 'Conatins Vitamin C, Vitamin K, Carotenoids, Amino acid.Vitamin C acts as a powerful antioxidant and also helps formation of collagen that is responsible for skin and hair health.', 150),
(3, 1, 19, 'KILOGRAM', 2, null, true, true, null, 'Contains Folic acid, Vitamin C and Amino acid .Vitamin C acts as a powerful antioxidant and also helps formation of collagen that is responsible for skin and hair health.', 50),
(4, 1, 85, 'ITEM', 12, null, true, true, null, 'Contains Potassium, Vitamin B6, Magnesium,Vitamin K, Magnese,Copper.Ginger is used for medicinal purposes to cure inflammation, pain and cough. It can also reduce menstrual cramps and is good for skin.', 43),
(5, 1, 30, 'KILOGRAM', 2, null, true, true, null, 'Cucumbers contain potassium, magnesium, vitamin C, folate, beta carotene, and vitamin K. Cucumbers help fight inflammation and keeps us super hydrated.', 67),
(6, 1, 70, 'KILOGRAM', 1, null, true, true, null, 'Contains, Fibre, Vitamin B1,Vitamin B2, Vitamin B6, Folic acid,Potassium. Garlic acts as a natural antibiotic, clears the skin and is good for Digestive system.', 30),
(7, 1, 40, 'KILOGRAM', 9, null, true, true, null, 'Contains Fibre, Folic acid, Potassium.Beetroot helps with improved blood flow, improve digestive health and fight inflammation.', 150),
(8, 1, 13, 'KILOGRAM', 9, null, true, true, null, 'Rich source of Vitamin Cand having Anti-Oxidants properties..Helps reduce acidity, act as an immunity booster and nourishes skin.', 86),
(9, 1, 100, 'KILOGRAM', 9, null, true, true, null, 'Contains Folic acid, Vitamin C, Vitamin K, .Potassium and Vitamin K aids digestion. Folic acid and Vitamin C act as a powerful antioxidants and also helps formation of collagen that is responsible for skin and hair health.', 78),
(10, 1, 86, 'KILOGRAM', 9, null, true, true, null, 'Rich source of Vitamin C, Contains Fibre Vitamin K, Amino acid.1. Vitamin C acts as a powerful antioxidant and also helps formation of collagen that is responsible for skin and hair health. Vitamin C helps in boosting immunity, keeping ailments at the bay.', 67),
(11, 1, 150, 'KILOGRAM', 9, null, true, true, null, 'Contains Folic Acid, Vitamin C, Vitamin K, .Folic acid and Vitamin C act as a powerful antioxidants and also helps formation of collagen that is responsible for skin and hair health.', 67),
(12, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'Contains Fibre, Flavonoids.1. Fiber is good for Digestive Health. Vitamin C acts as a powerful antioxidant and also helps formation of collagen that is responsible for skin and hair health. Vitamin C helps in boosting immunity, keeping ailments at the bay.', 90),
(13, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'Contain Folic acid, Vitamin C, Carotenes, Amino Acid, .Papaya helps in digestion, has anti inflammatory properties acts as a skin cleanser and boosts immunity.', 90),
(14, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'Rich source of Vitamin C, Contains Vitamin K, Amino acid.Vitamin C helps improving the skin health, boosts immune system and keep digestive system up to the mark.', 148),
(15, 1, 18, 'LITRE', 9, null, true, true, null, '', 54),
(16, 1, 10, 'LITRE', 9, null, true, true, null, '', 54),
(17, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'This true American original has robust flavor and is tender enough to simply sear to a perfect velvety pink. Or try marinating to impart even more juicy tenderness. You might know this well-marbled loin cut as a New York or Kansas City strip. Always performs well — broiled, grilled or pan-fried.', 148),
(18, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'These lamb stir-fry strips are a great addition to your easy, weeknight meal rotation. Cut from the leg, they have a versatile, mild taste that makes them great for everything from Sichuan stir-fries, curries, or stuffed into a pita with tzatziki and hummus.', 148),
(19, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'We partnered with Artisan Meats in upstate New York to turn our FreshDirect pasture-raised heritage pork into high-quality sausages. Smoked and packed with flavorful New York cheddar, this delicious, fully-cooked pork sausage is seasoned with onion, garlic, marjoram, and a touch of spicy jalapeño.', 148),
(20, 1, 32, 'KILOGRAM', 9, null, true, true, null, 'If grilling is your thing, this succulent slab was born to barbecue. We''ve removed the breastbone, making for uniform ribs. That makes them much easier to handle on the grill. Rub with a combination of salt, pepper, sugar, chili powder, paprika, and cumin. Then grill them slowly over indirect heat.', 148);


