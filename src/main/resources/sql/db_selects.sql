--1
SELECT pc.model, pc.hd, pc.speed FROM pc WHERE pc.price < 500;
--2
SELECT p.maker FROM product p WHERE p.type = 'printer';
--3
SELECT l.model, l.hd, l.screen FROM laptop l WHERE l.price > 1000;
--4
SELECT * FROM printer pr WHERE pr.color = 'y';
--5
SELECT pc.model, pc.hd, pc.speed FROM pc WHERE pc.price < 600 AND pc.cd = '12x' OR pc.cd = '24x';
--6
SELECT pr.model, l.speed FROM product pr LEFT JOIN laptop l ON l.model = pr.model
WHERE pr.type = 'laptop' AND l.hd > 100;
--7 B->Samsung
SELECT pr.maker, pr.model, l.price FROM product pr LEFT JOIN laptop l ON l.model = pr.model
WHERE pr.type = 'laptop' AND pr.maker = 'Samsung';
--8
SELECT pr.maker FROM product pr LEFT JOIN pc ON pc.model = pr.model WHERE pr.type = 'pc' OR pr.type = 'laptop'
GROUP BY pr.maker HAVING COUNT(pr.type) = COUNT(pc.code);
--9
SELECT pr.maker FROM product pr LEFT JOIN pc ON pc.model = pr.model WHERE pc.speed > 450;
--10
SELECT pr.model, pr.price FROM printer pr ORDER BY pr.price DESC LIMIT 1;
--11
SELECT ROUND(AVG(p.speed)) FROM pc p;
--12
SELECT ROUND(AVG(l.speed)) FROM laptop l WHERE l.price < 1000;
--13 A->Asus
SELECT ROUND(AVG(p.speed)) FROM pc p left join product pr on pr.model = p.model WHERE pr.maker = 'Asus';
--14
SELECT p.speed, ROUND(AVG(p.price), 2) AS price FROM pc p GROUP BY p.speed;
--15
SELECT p.hd FROM pc p GROUP BY p.hd HAVING COUNT(p.price) > 1;
--16
SELECT MAX(pc.model), MIN(pc.model), pc.speed, pc.ram FROM pc
GROUP BY pc.speed, pc.ram HAVING MAX(pc.model) > MIN(pc.model);
--17
SELECT p.type, p.model, l.speed FROM product p LEFT JOIN laptop l ON p.model = l.model
                                               LEFT JOIN pc ON p.model = pc.model WHERE l.speed < (SELECT MIN (speed) FROM pc) AND p.type = 'laptop';
--18
SELECT p.type, pr.price FROM product p RIGHT JOIN printer pr ON p.model = pr.model ORDER BY pr.price LIMIT 1;
--19
SELECT p.maker, ROUND(AVG(l.screen)) FROM product p RIGHT JOIN laptop l ON p.model = l.model GROUP BY p.maker
--20
SELECT p.maker, COUNT(pc.model) FROM product p RIGHT JOIN pc ON p.model = pc.model
GROUP BY p.maker HAVING COUNT(pc.model) > 2;
--21
SELECT p.maker, MAX(pc.price) FROM product p RIGHT JOIN pc ON p.model = pc.model GROUP BY p.maker;
--22
SELECT pc.speed, ROUND(AVG(pc.price)) FROM pc GROUP BY pc.speed;
--23
SELECT pr.maker FROM product pr LEFT JOIN pc ON pc.model = pr.model LEFT JOIN laptop l ON l.model = pr.model
WHERE  (pr.type = 'pc' OR pr.type = 'laptop') GROUP BY pr.maker
HAVING COUNT(pr.type) != COUNT(pc.code) AND COUNT(pc.code) != 0 AND MAX(pc.speed) > 750 AND MAX(l.speed) > 750;
--24
SELECT p.model FROM product p RIGHT JOIN pc ON pc.model = p.model ORDER BY pc.price DESC LIMIT 1;
--25
SELECT p.maker FROM product p WHERE type = 'printer' AND
        p.maker IN (
        SELECT p.maker FROM product p WHERE p.model IN (
            SELECT pc.model FROM pc WHERE pc.speed = (
                SELECT MAX(pc.speed) FROM (
                                              SELECT pc.speed FROM pc WHERE pc.ram = (
                                                  SELECT MIN(pc.ram) FROM pc)) AS pc)
        )
    );
