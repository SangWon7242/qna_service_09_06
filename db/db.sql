# 운영용 DB
DROP DATABASE IF EXISTS qnaService;
CREATE DATABASE qnaService;
USE qnaService;

# 개발용 DB
DROP DATABASE IF EXISTS qnaService__dev;
CREATE DATABASE qnaService__dev;
USE qnaService__dev;

SHOW tables;

# 테스트용 DB
DROP DATABASE IF EXISTS qnaService__test;
CREATE DATABASE qnaService__test;
USE qnaService__test;

SHOW tables;