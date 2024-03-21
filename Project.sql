CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE administrators (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100)
);

INSERT INTO doctors(name, specialization)
VALUES('Dr.Strange', 'engioplasty'), ('Dr.Watson', 'medicalspecialist'), ('Dr.Lana', 'SkinTransplant');

CREATE TABLE nurses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    time VARCHAR(255),
    doctor_id INT,
    patient_id INT,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE patient_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
	diagnosis VARCHAR(255),
    treatment VARCHAR(255),
    payment INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

	CREATE TABLE reports (
		id INT AUTO_INCREMENT PRIMARY KEY,
		patient_id INT,
		diagnosis VARCHAR(255),
		treatment VARCHAR(255),
		FOREIGN KEY (patient_id) REFERENCES patient_records(patient_id)
	);

CREATE TABLE billings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    invoice_details TEXT,
    payment_status VARCHAR(50)
    #FOREIGN KEY (patient_id) REFERENCES patients(id)
);

INSERT INTO billings(patient_id, invoice_details)
VALUES(1, 2345),(2,2222),(3,5432),(4,764);

CREATE TABLE inventory_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT
);

CREATE TABLE feedbacks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    message TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);
