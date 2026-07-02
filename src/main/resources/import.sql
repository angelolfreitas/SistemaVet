-- DELETEs opcionais para garantir limpeza (se você não estiver usando create-drop)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE item_receita;
TRUNCATE TABLE receita;
TRUNCATE TABLE atendimento;
TRUNCATE TABLE tutor_has_pets;
TRUNCATE TABLE pets;
TRUNCATE TABLE tutor;
TRUNCATE TABLE veterinario;
TRUNCATE TABLE usuario;
TRUNCATE TABLE endereco;
TRUNCATE TABLE medicamento;
SET FOREIGN_KEY_CHECKS = 1;

-- Inserções (Ordem hierárquica respeitada)
INSERT INTO endereco (id_endereco, logradouro, numero, bairro, cidade, estado) VALUES (1, 'Rua das Flores', '123', 'Cohama', 'São Luís', 'MA');
INSERT INTO endereco (id_endereco, logradouro, numero, bairro, cidade, estado) VALUES (2, 'Av. dos Holandeses', '456', 'Calhau', 'São Luís', 'MA');

INSERT INTO usuario (id_usuario, nome, email, senha, cpf, role, id_endereco) VALUES (1, 'Dr. Roberto Mendes', 'roberto@uema.br', '$2a$10$By1jOnVjVpxoFbe7g9vPLeKzV4u4G/w3wIlyM.Z4L9Y3oZg6gZg6.', '111.111.111-11', 1, 1);
INSERT INTO usuario (id_usuario, nome, email, senha, cpf, role, id_endereco) VALUES (2, 'Mariana Souza', 'mariana@gmail.com', '$2a$10$By1jOnVjVpxoFbe7g9vPLeKzV4u4G/w3wIlyM.Z4L9Y3oZg6gZg6.', '222.222.222-22', 2, 2);
INSERT INTO usuario (id_usuario, nome, email, senha, cpf, role, id_endereco) VALUES (3, 'Gabriel Amado', 'gabriel@gmail.com', '$2a$10$By1jOnVjVpxoFbe7g9vPLeKzV4u4G/w3wIlyM.Z4L9Y3oZg6gZg6.', '333.333.333-33', 2, 2);

INSERT INTO veterinario (id_veterinario, crmv, especialidade) VALUES (1, 'CRMV-MA 9876', 'Clínica Geral');
INSERT INTO tutor (id_tutor, data_cadastro) VALUES (2, '2026-01-15');
INSERT INTO tutor (id_tutor, data_cadastro) VALUES (3, '2026-02-20');

INSERT INTO pets (id_pet, nome, especie, raça, data_nascimento, peso_atual) VALUES (1, 'Mingau', 'Gato', 'Persa', '2023-05-12', 4.50);
INSERT INTO pets (id_pet, nome, especie, raça, data_nascimento, peso_atual) VALUES (2, 'Apollo', 'Cão', 'Golden Retriever', '2024-01-20', 32.00);

INSERT INTO tutor_has_pets (id_tutor, id_pet) VALUES (2, 1);
INSERT INTO tutor_has_pets (id_tutor, id_pet) VALUES (3, 2);

INSERT INTO medicamento (id_medicamento, nome_comercial, principio_ativo, concentracao, quantidade_estoque) VALUES (1, 'Maxicam', 'Meloxicam', '2mg', 50);
INSERT INTO medicamento (id_medicamento, nome_comercial, principio_ativo, concentracao, quantidade_estoque) VALUES (2, 'Simparic', 'Sarolaner', '40mg', 30);

INSERT INTO atendimento (id_atendimento, data, hora, status, motivo_consulta, peso_atendimento, anamnese, id_pet, id_tutor, id_veterinario) VALUES (1, '2026-06-01', '10:00:00', 1, 'Consulta de Rotina', 29.00, 'Animal ativo e saudável.', 2, 3, 1);
INSERT INTO atendimento (id_atendimento, data, hora, status, motivo_consulta, peso_atendimento, anamnese, id_pet, id_tutor, id_veterinario) VALUES (2, '2026-07-02', '14:30:00', 1, 'Retorno Vacinal', 32.00, 'Ganho de peso.', 2, 3, 1);

INSERT INTO receita (id_receita, data_emissao, orientacoes_gerais, id_atendimento) VALUES (1, '2026-07-02', 'Dar após as refeições.', 2);
INSERT INTO item_receita (id_receita, id_medicamento, dosagem, frequencia_tempo) VALUES (1, 1, '1 comprimido', '24h');
INSERT INTO item_receita (id_receita, id_medicamento, dosagem, frequencia_tempo) VALUES (1, 2, '1 tablete', 'Mensal');