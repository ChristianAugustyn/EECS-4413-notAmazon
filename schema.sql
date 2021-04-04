CREATE TABLE Book (
    bid         VARCHAR(20) NOT NULL,
    title       VARCHAR(75) NOT NULL,
    price       DECIMAL(4, 2) NOT NULL,
    category    VARCHAR(30) NOT NULL,
    PRIMARY KEY(bid),
    CONSTRAINT category_check CHECK
        (
            category IN ('Adventure Fiction', 'Autobiography', 'Comedy', 'Fantasy', 'Fiction', 'Historical Fiction',
                    'Horror', 'Science Fiction', 'Modernist Fiction', 'Non-Fiction', 'Philosophical Fiction',
                    'Picaresque Novel', 'Psychological Fiction', 'Romance', 'Victorian Literature')
        )
);

INSERT INTO Book (bid, title, price, category) values
    ('b001', 'Alice In Wonderland', 44.54, 'Fantasy'),
    ('b002', 'A Tale of Two Cities', 16.82, 'Historical Fiction'),
    ('b003', 'Common Sense', 12.00, 'Non-Fiction'),
    ('b004', 'Dracula', 37.59, 'Horror'),
    ('b005', 'Frankenstein', 25.39, 'Science Fiction'),
    ('b006', 'Adventures of Huckleberry Finn', 30.36, 'Picaresque Novel'),
    ('b007', 'Jane Eyre', 16.60, 'Victorian Literature'),
    ('b008', 'Narrative of the Captivity and Restoration of Mrs. Mary Rowlandson', 32.80, 'Autobiography'),
    ('b009', 'The Metamorphisis', 16.95, 'Modernist Fiction'),
    ('b010', 'Moby Dick', 17.61, 'Adventure Fiction'),
    ('b011', 'Peter Pan', 18.39, 'Fantasy'),
    ('b012', 'Pride and Prejudice', 16.82, 'Romance'),
    ('b013', 'The Adventures of Sherlock Holmes', 29.76, 'Fiction'),
    ('b014', 'Siddhartha', 7.58, 'Philosophical Fiction'),
    ('b015', 'The Importance of Being Earnest', 7.84, 'Comedy'),
    ('b016', 'The Legend of Sleepy Hollow', 58.93, 'Horror'),
    ('b017', 'The Time Machine', 4.00, 'Science Fiction'),
    ('b018', 'The Adventures of Tom Sawyer', 11.50, 'Picaresque Novel'),
    ('b019', 'The Yellow Wallpaper', 27.89, 'Psychological Fiction'),
    ('b020', 'War and Peace', 13.81, 'Historical Fiction');

CREATE TABLE Address (
    id          INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    street      VARCHAR(100) NOT NULL,
    province    VARCHAR(20) NOT NULL,
    country     VARCHAR(20) NOT NULL,
    zip         VARCHAR(20) NOT NULL,
    phone       VARCHAR(20),
    PRIMARY KEY(id)
);

CREATE TABLE PO (
    id         INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    lname      VARCHAR(20) NOT NULL,
    fname      VARCHAR(20) NOT NULL,
    status     VARCHAR(10) NOT NULL,
    address_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (address_id) REFERENCES Address (id) ON DELETE CASCADE,
    CONSTRAINT status_check CHECK
        (
            status IN ('ORDERED','PROCESSED','DENIED')
        )
);

CREATE TABLE POItem (
    id      INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    bid     VARCHAR(20) NOT NULL,
    price   DECIMAL(4, 2) NOT NULL,
    PRIMARY KEY(id,bid),
    FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
    FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
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