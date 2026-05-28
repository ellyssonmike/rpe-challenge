CREATE TABLE IF NOT EXISTS paymentmethods (
    id INT PRIMARY KEY,
    description VARCHAR(255)
);

INSERT INTO paymentmethods (id, description)
VALUES
    (1, 'PIX'),
    (2, 'CREDIT_CARD'),
    (3, 'DEBIT_CARD')
ON CONFLICT (id) DO NOTHING;