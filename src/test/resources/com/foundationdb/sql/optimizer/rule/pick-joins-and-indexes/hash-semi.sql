SELECT * FROM t1 WHERE EXISTS (SELECT * FROM t2 WHERE t1.c1 = t2.c1)