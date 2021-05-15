package ru.job4j.cars.model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;
/**
 * Class represents user's credential storage.
 * For more information on the relationship of the entity, see db/cars_scheme_png and db/scheme.sql
 *
 *@author AndrewMs
 *@version 1.0
 */
@Entity
@Table(name = "credentials")
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private byte[] salt;
    private byte[] hash;

    public UserCredentials(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.salt = generateSalt();
        this.hash = generateHash(password, this.salt);
    }

    public UserCredentials() {
    }

    /**
     * Validates a user's password by comparing the password's hash with the saved one.
     * @param password password
     * @return true, if the validation was success, false otherwise.
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean validate(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
           return Arrays.equals(this.hash, generateHash(password, this.salt));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCredentials that = (UserCredentials) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserCredentials{"
                + "id=" + id
                + ", salt='" + Arrays.toString(salt) + '\''
                + ", hash='" + Arrays.toString(hash) + '\''
                + '}';
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] generateHash(String str, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }
}
