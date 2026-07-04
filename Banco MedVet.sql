CREATE DATABASE IF NOT EXISTS `clinica_veterinaria`;
USE `clinica_veterinaria`;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `Endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Endereco` ;

CREATE TABLE IF NOT EXISTS `Endereco` (
  `id_endereco` BIGINT NOT NULL AUTO_INCREMENT,
  `cep` CHAR(8) NOT NULL,
  `logradouro` VARCHAR(150) NOT NULL,
  `numero` VARCHAR(10) NOT NULL,
  `complemento` VARCHAR(50) NULL,
  `bairro` VARCHAR(80) NOT NULL,
  `cidade` VARCHAR(80) NOT NULL,
  `estado` CHAR(2) NOT NULL,
  PRIMARY KEY (`id_endereco`),
  UNIQUE INDEX `id_Endereço_UNIQUE` (`id_endereco` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Usuario` ;

CREATE TABLE IF NOT EXISTS `Usuario` (
  `id_usuario` BIGINT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(14) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `telefone` VARCHAR(15) NOT NULL,
  `id_endereco` BIGINT NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `id_usuario_UNIQUE` (`id_usuario` ASC) VISIBLE,
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_Usuario_Endereco1_idx` (`id_endereco` ASC) VISIBLE,
  CONSTRAINT `fk_Usuario_Endereco1`
    FOREIGN KEY (`id_endereco`)
    REFERENCES `Endereco` (`id_endereco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tutor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tutor` ;

CREATE TABLE IF NOT EXISTS `tutor` (
  `id_tutor` BIGINT NOT NULL,
  `data_cadastro` DATE NOT NULL,
  PRIMARY KEY (`id_tutor`),
  INDEX `fk_tutor_Usuario1_idx` (`id_tutor` ASC) VISIBLE,
  CONSTRAINT `fk_tutor_Usuario1`
    FOREIGN KEY (`id_tutor`)
    REFERENCES `Usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pets`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `pets` ;

CREATE TABLE IF NOT EXISTS `pets` (
  `id_pet` BIGINT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `especie` VARCHAR(30) NOT NULL,
  `raça` VARCHAR(50) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `peso_atual` DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (`id_pet`),
  UNIQUE INDEX `id_pet_UNIQUE` (`id_pet` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `veterinario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `veterinario` ;

CREATE TABLE IF NOT EXISTS `veterinario` (
  `id_veterinario` BIGINT NOT NULL,
  `senha` VARCHAR(255) NULL,
  `crmv` VARCHAR(15) NOT NULL,
  `especialidade` VARCHAR(50) NULL,
  PRIMARY KEY (`id_veterinario`),
  UNIQUE INDEX `crmv_UNIQUE` (`crmv` ASC) VISIBLE,
  INDEX `fk_veterinario_Usuario1_idx` (`id_veterinario` ASC) VISIBLE,
  CONSTRAINT `fk_veterinario_Usuario1`
    FOREIGN KEY (`id_veterinario`)
    REFERENCES `Usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Atendimento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Atendimento` ;

CREATE TABLE IF NOT EXISTS `Atendimento` (
  `id_Atendimento` BIGINT NOT NULL,
  `data` DATE NOT NULL,
  `hora` TIME NOT NULL,
  `status` ENUM('agendado', 'em andamento', 'concluido', 'cancelado') NOT NULL,
  `motivo_consulta` VARCHAR(200) NOT NULL,
  `peso_atendimento` DECIMAL(5,2) NOT NULL,
  `anamnese` TEXT NOT NULL,
  `id_pet` BIGINT NOT NULL,
  `id_tutor` BIGINT NOT NULL,
  `id_veterinario` BIGINT NOT NULL,
  PRIMARY KEY (`id_Atendimento`),
  INDEX `fk_Atendimento_pets1_idx` (`id_pet` ASC) VISIBLE,
  INDEX `fk_Atendimento_tutor1_idx` (`id_tutor` ASC) VISIBLE,
  INDEX `fk_Atendimento_veterinario1_idx` (`id_veterinario` ASC) VISIBLE,
  CONSTRAINT `fk_Atendimento_pets1`
    FOREIGN KEY (`id_pet`)
    REFERENCES `pets` (`id_pet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Atendimento_tutor1`
    FOREIGN KEY (`id_tutor`)
    REFERENCES `tutor` (`id_tutor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Atendimento_veterinario1`
    FOREIGN KEY (`id_veterinario`)
    REFERENCES `veterinario` (`id_veterinario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `receita` ;

CREATE TABLE IF NOT EXISTS `receita` (
  `id_receita` BIGINT NOT NULL,
  `data_emissao` DATE NOT NULL,
  `orientacoes_gerais` TEXT NOT NULL,
  `id_Atendimento` BIGINT NOT NULL,
  PRIMARY KEY (`id_receita`),
  UNIQUE INDEX `receita_id_UNIQUE` (`id_receita` ASC) VISIBLE,
  INDEX `fk_receita_Atendimento1_idx` (`id_Atendimento` ASC) VISIBLE,
  CONSTRAINT `fk_receita_Atendimento1`
    FOREIGN KEY (`id_Atendimento`)
    REFERENCES `Atendimento` (`id_Atendimento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tutor_has_pets`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tutor_has_pets` ;

CREATE TABLE IF NOT EXISTS `tutor_has_pets` (
  `id_tutor` BIGINT NOT NULL,
  `id_pet` BIGINT NOT NULL,
  PRIMARY KEY (`id_tutor`, `id_pet`),
  INDEX `fk_tutor_has_pets_pets1_idx` (`id_pet` ASC) VISIBLE,
  INDEX `fk_tutor_has_pets_tutor1_idx` (`id_tutor` ASC) VISIBLE,
  CONSTRAINT `fk_tutor_has_pets_tutor1`
    FOREIGN KEY (`id_tutor`)
    REFERENCES `tutor` (`id_tutor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tutor_has_pets_pets1`
    FOREIGN KEY (`id_pet`)
    REFERENCES `pets` (`id_pet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Recepcionista`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Recepcionista` ;

CREATE TABLE IF NOT EXISTS `Recepcionista` (
  `id_recepcionista` BIGINT NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `data_admissao` DATE NOT NULL,
  `turno` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_recepcionista`),
  CONSTRAINT `fk_Recepcionista_Usuario1`
    FOREIGN KEY (`id_recepcionista`)
    REFERENCES `Usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `medicamento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `medicamento` ;

CREATE TABLE IF NOT EXISTS `medicamento` (
  `id_medicamento` BIGINT NOT NULL,
  `nome_comercial` VARCHAR(100) NOT NULL,
  `principio_ativo` VARCHAR(100) NOT NULL,
  `concentracao` VARCHAR(45) NOT NULL,
  `quantidade_estoque` BIGINT NULL,
  PRIMARY KEY (`id_medicamento`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `item_receita`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `item_receita` ;

CREATE TABLE IF NOT EXISTS `item_receita` (
  `id_medicamento` BIGINT NOT NULL,
  `id_receita` BIGINT NOT NULL,
  `dosagem` VARCHAR(45) NOT NULL,
  `frequencia_tempo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_medicamento`, `id_receita`),
  INDEX `fk_medicamento_has_receita_receita1_idx` (`id_receita` ASC) VISIBLE,
  INDEX `fk_medicamento_has_receita_medicamento1_idx` (`id_medicamento` ASC) VISIBLE,
  CONSTRAINT `fk_medicamento_has_receita_medicamento1`
    FOREIGN KEY (`id_medicamento`)
    REFERENCES `medicamento` (`id_medicamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_medicamento_has_receita_receita1`
    FOREIGN KEY (`id_receita`)
    REFERENCES `receita` (`id_receita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
