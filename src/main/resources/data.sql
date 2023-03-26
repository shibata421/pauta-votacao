CREATE TABLE IF NOT EXISTS pautas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    horario_Fim dateTime
);

CREATE TABLE IF NOT EXISTS associados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS votos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    associado_id INT NOT NULL,
    pauta_id INT NOT NULL,
    voto VARCHAR(3),
    FOREIGN KEY (associado_id) REFERENCES associados(id),
    FOREIGN KEY (pauta_id) REFERENCES pautas(id)
);

INSERT INTO pautas(id, descricao, horario_Fim) VALUES (1, 'TESTE', '2023-03-26 18:00:00');

INSERT INTO associados(id, nome, cpf) VALUES (1, 'Fernando', '12345678901');
INSERT INTO associados(id, nome, cpf) VALUES (2, 'Joao', '78945612301');
INSERT INTO associados(id, nome, cpf) VALUES (3, 'Maria', '45678912301');