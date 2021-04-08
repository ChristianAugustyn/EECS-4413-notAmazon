CREATE TABLE Address (
    id          INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lname       VARCHAR(60) NOT NULL,
    fname       VARCHAR(60) NOT NULL,
    street      VARCHAR(105) NOT NULL,
    city        VARCHAR(105) NOT NULL,
    province    VARCHAR(105) NOT NULL,
    country     VARCHAR(57) NOT NULL,
    zip         VARCHAR(20) NOT NULL,
    phone       VARCHAR(20),
    addressType VARCHAR(8) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT addressType_check CHECK
        (
            addressType in ('BILLING', 'SHIPPING', 'BOTH')
        )
);

CREATE TABLE Users (
    userId   VARCHAR(320) NOT NULL,
    userPw   VARCHAR(1000) NOT NULL,
    lname    VARCHAR(60) NOT NULL,
    fname    VARCHAR(60) NOT NULL,
    shipping INT NOT NULL,
    billing  INT NOT NULL,
    token    VARCHAR(26),
    PRIMARY KEY(userId),
    FOREIGN KEY(shipping) REFERENCES Address(id),
    FOREIGN KEY(billing) REFERENCES Address(id)
);

CREATE TABLE Book (
    bid         VARCHAR(20) NOT NULL,
    title       VARCHAR(75) NOT NULL,
    price       DECIMAL(4, 2) NOT NULL,
    category    VARCHAR(30) NOT NULL,
    cover       VARCHAR(80) NOT NULL,
    PRIMARY KEY(bid),
    CONSTRAINT category_check CHECK
        (
            category IN ('Adventure Fiction', 'Autobiography', 'Comedy', 'Fantasy', 'Fiction', 'Historical Fiction',
                    'Horror', 'Science Fiction', 'Modernist Fiction', 'Non-Fiction', 'Philosophical Fiction',
                    'Picaresque Novel', 'Psychological Fiction', 'Romance', 'Victorian Literature')
        )
);

INSERT INTO Book (bid, title, price, category, cover) values
    ('b001', 'Alice In Wonderland', 44.54, 'Fantasy', 'http://covers.openlibrary.org/b/olid/OL27002614M-L.jpg'),
    ('b002', 'A Tale of Two Cities', 16.82, 'Historical Fiction', 'http://covers.openlibrary.org/b/olid/-L.jpg'),
    ('b003', 'Common Sense', 12.00, 'Non-Fiction', 'http://covers.openlibrary.org/b/olid/-L.jpg'),
    ('b004', 'Dracula', 37.59, 'Horror', 'http://covers.openlibrary.org/b/olid/OL17848562M-L.jpg'),
    ('b005', 'Frankenstein', 25.39, 'Science Fiction', 'http://covers.openlibrary.org/b/olid/OL6942528M-L.jpg'),
    ('b006', 'Adventures of Huckleberry Finn', 30.36, 'Picaresque Novel', 'http://covers.openlibrary.org/b/olid/OL2030579M-L.jpg'),
    ('b007', 'Jane Eyre', 16.60, 'Victorian Literature', 'http://covers.openlibrary.org/b/olid/OL14015946M-L.jpg'),
    ('b008', 'Narrative of the Captivity and Restoration of Mrs. Mary Rowlandson', 32.80, 'Autobiography', 'http://covers.openlibrary.org/b/olid/OL27770747M-L.jpg'),
    ('b009', 'The Metamorphisis', 16.95, 'Modernist Fiction', 'http://covers.openlibrary.org/b/olid/OL8729972M-L.jpg'),
    ('b010', 'Moby Dick', 17.61, 'Adventure Fiction', 'http://covers.openlibrary.org/b/olid/OL32062320M-L.jpg'),
    ('b011', 'Peter Pan', 18.39, 'Fantasy', 'http://covers.openlibrary.org/b/olid/OL32132888M-L.jpg'),
    ('b012', 'Pride and Prejudice', 16.82, 'Romance', 'http://covers.openlibrary.org/b/olid/OL8327968M-L.jpg'),
    ('b013', 'The Adventures of Sherlock Holmes', 29.76, 'Fiction', 'http://covers.openlibrary.org/b/olid/OL27919751M-L.jpg'),
    ('b014', 'Siddhartha', 7.58, 'Philosophical Fiction', 'http://covers.openlibrary.org/b/olid/OL9525932M-L.jpg'),
    ('b015', 'The Importance of Being Earnest', 7.84, 'Comedy', 'http://covers.openlibrary.org/b/olid/OL27704750M-L.jpg'),
    ('b016', 'The Legend of Sleepy Hollow', 58.93, 'Horror', 'http://covers.openlibrary.org/b/olid/OL23155118M-L.jpg'),
    ('b017', 'The Time Machine', 4.00, 'Science Fiction', 'http://covers.openlibrary.org/b/olid/OL28166043M-L.jpg'),
    ('b018', 'The Adventures of Tom Sawyer', 11.50, 'Picaresque Novel', 'http://covers.openlibrary.org/b/olid/OL3410324M-L.jpg'),
    ('b019', 'The Yellow Wallpaper', 27.89, 'Psychological Fiction', 'http://covers.openlibrary.org/b/olid/OL27767475M-L.jpg'),
    ('b020', 'War and Peace', 13.81, 'Historical Fiction', 'http://covers.openlibrary.org/b/olid/OL28288911M-L.jpg');

CREATE TABLE PO (
    id         INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    userid     VARCHAR(320) NOT NULL,
    status     VARCHAR(10) NOT NULL,
    billing INT NOT NULL,
    shipping INT NOT NULL,
    orderdate DATE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(userid) REFERENCES Users(userid),
    FOREIGN KEY(billing) REFERENCES Address(id),
    FOREIGN KEY(shipping) REFERENCES Address(id),
    CONSTRAINT status_check CHECK
        (
            status IN ('ORDERED','PROCESSED','DENIED')
        )
);

CREATE TABLE POItem (
    id      INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    poid INT NOT NULL,
    bid     VARCHAR(20) NOT NULL,
    price   DECIMAL(4, 2) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE,
    FOREIGN KEY(poid) REFERENCES PO(id) ON DELETE CASCADE
);

CREATE TABLE VisitEvent (
    day         VARCHAR(8) NOT NULL,
    bid         VARCHAR(20) not null,
    eventtype   VARCHAR(10) NOT NULL,
    FOREIGN KEY(bid) REFERENCES Book(bid),
    CONSTRAINT eventtype_check CHECK
        (
            eventtype IN ('VIEW','CART','PURCHASE')
        )
);

INSERT INTO VisitEvent (day, bid, eventtype) VALUES
    ('12202015', 'b001', 'VIEW'),
    ('12242015', 'b001', 'CART'),
    ('12252015', 'b001', 'PURCHASE');

CREATE TABLE ProdReviews (
    id      INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    bid     VARCHAR(20) NOT NULL,
    rtitle  VARCHAR(125) NOT NULL,
    lname   VARCHAR(30) NOT NULL,
    fname   VARCHAR(30) NOT NULL,
    rating  INT NOT NULL,
    message VARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(bid) REFERENCES Book(bid),
    CONSTRAINT rating_check
        CHECK (rating >= 0 and rating <= 5)
);

INSERT INTO ProdReviews (bid, rtitle, lname, fname, rating, message) VALUES
    ('b001', 'Test Title 1', 'Gravel', 'James', 3, 'Test review book 1'),
    ('b002', 'Test Title 2', 'Gravel', 'James', 5, 'Test review book 2');

insert into ADDRESS (lname, fname, street, city, province, country, zip, phone, addressType) values
    ('smith', 'john', '123 Street', 'myCity', 'myProv', 'myCountry', '123456', '123456789', 'BILLING'),
    ('Ford', 'Henry', '321 Street', 'Toronto', 'Ontario', 'Canada', '654321', '9055456212', 'BOTH');
    
insert into USERS (USERID, USERPW, LNAME, FNAME, SHIPPING, BILLING) values
	('johnSmith', 'myPassword', 'Smith', 'John', 2, 2);
    
    
