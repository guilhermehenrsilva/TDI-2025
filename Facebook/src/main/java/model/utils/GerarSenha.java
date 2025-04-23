package model.utils;

import model.utils.PasswordEncryptor;

public class GerarSenha {
    public static void main(String[] args) {
        String senha = "1234"; // aqui você coloca a senha que quiser
        String hash = PasswordEncryptor.hashPassword(senha);
        System.out.println("Hash da senha: " + hash);
    }
}

