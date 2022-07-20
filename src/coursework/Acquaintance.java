package coursework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Acquaintance implements Serializable {
    private String firstName;
    private String middleName;
    private String lastName;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private String homeAddress;
    private int phoneNumber;
    private int secondPhoneNumber;
    private String sex;
    private String interests;
    private String zodiacSign;

    public Acquaintance(String firstName, String middleName, String lastName,
                        int birthDay, int birthMonth, int birthYear, int phoneNumber,
                        int secondPhoneNumber, String sex, String interests, String zodiacSign) {
        try {
            this.setFirstName(firstName);
            this.setMiddleName(middleName);
            this.setLastName(lastName);
            this.setBirthDay(birthDay);
            this.setBirthMonth(birthMonth);
            this.setBirthYear(birthYear);
            this.setPhoneNumber(phoneNumber);
            this.setSecondPhoneNumber(secondPhoneNumber);
            this.setSex(sex);
            this.setInterests(interests);
            this.setZodiacSign(zodiacSign);
        } catch (Exception e) {
            throw new IllegalArgumentException("Попытка создания некорректного знакомого");
        }
    }
    public Acquaintance() {
    }

    public String getFirstName() {
        return firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getBirthDay() {
        return birthDay;
    }
    public int getBirthMonth() {
        return birthMonth;
    }
    public int getBirthYear() {
        return birthYear;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public int getSecondPhoneNumber() {
        return secondPhoneNumber;
    }
    public String getSex() {
        return sex;
    }
    public String getInterests() {
        return interests;
    }
    public String getZodiacSign() {
        return zodiacSign;
    }

    public void setFirstName(String firstName) throws IllegalArgumentException {
        if (Validation.isFirstCapital(firstName) && firstName.length() < 25) {
            this.firstName = Validation.cleanString(firstName);
        } else {
            throw new IllegalArgumentException("Имя знакомого должно начинаться с большой буквы и быть меньше чем 25 символов");
        }
    }

    public void setMiddleName(String middleName) throws IllegalArgumentException {
        if (Validation.isFirstCapital(middleName) && middleName.length() < 25) {
            this.middleName = Validation.cleanString(middleName);
        } else {
            throw new IllegalArgumentException("Отчество знакомого должно начинаться с большой буквы и быть меньше чем 25 символов");
        }
    }

    public void setLastName(String lastName) throws IllegalArgumentException {
        if (Validation.isFirstCapital(lastName) && lastName.length() < 25) {
            this.lastName = Validation.cleanString(lastName);
        } else {
            throw new IllegalArgumentException("Фамилия знакомого должно начинаться с большой буквы и быть меньше чем 25 символов");
        }
    }

    public void setBirthDay(int birthDay) throws IllegalArgumentException {
        if (birthDay >= 1 && birthDay <= 31) {
            this.birthDay = birthDay;
        } else {
            throw new IllegalArgumentException("День рождения должен быть от 1 до 31");
        }
    }

    public void setBirthMonth(int birthMonth) throws IllegalArgumentException {
        if (birthDay >= 1 && birthDay <= 12) {
            this.birthMonth = birthMonth;
        } else {
            throw new IllegalArgumentException("Месяц рождения должен быть от 1 до 12");
        }
    }

    public void setBirthYear(int birthYear) throws IllegalArgumentException {
        if (birthYear >= 1900 && birthYear <= 2022) {
            this.birthYear = birthYear;
        } else {
            throw new IllegalArgumentException("Год рождения должен быть от 1900 до 2022");
        }
    }

    public void setHomeAddress(String homeAddress) throws IllegalArgumentException {
        if (Validation.isFirstCapital(homeAddress) && homeAddress.length() < 100) {
            this.homeAddress = Validation.cleanString(homeAddress);
        } else {
            throw new IllegalArgumentException("Адрес знакомого должен начинаться с большой буквы и быть меньше чем 100 символов");
        }
    }

    public void setPhoneNumber(int phoneNumber) throws IllegalArgumentException {
        if (String.valueOf(phoneNumber).length() == 12) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Номер телефона должен быть длиной в ровно 12 символов");
        }
    }

    public void setSecondPhoneNumber(String secondPhoneNumber) throws IllegalArgumentException {
        // Проверяем если строка содержит только цифры и длиной в 12 символов или если строка пустая (поле не заполнено)
        if ((secondPhoneNumber.matches("[0-9]+") && secondPhoneNumber.length() == 12) || secondPhoneNumber == "") {
            this.secondPhoneNumber = Integer.parseInt(secondPhoneNumber);
        } else {
            throw new IllegalArgumentException("Второй номер телефона должен быть длиной в ровно 12 символов");
        }
    }


    public void setSex(String sex) throws IllegalArgumentException {
        if (sex == "F" || sex == "M") {
            this.sex = sex;
        } else {
            throw new IllegalArgumentException("Допустимый пол знакомого: M или F");
        }
    }

    public void setInterests(String interests) throws IllegalArgumentException {
        this.interests = interests;
    }

    public void setZodiacSign(String zodiacSign) throws IllegalArgumentException {
        this.zodiacSign = zodiacSign;
    }

    @Override
    public String toString() {
        return "Acquaintance{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                ", birthMonth=" + birthMonth +
                ", birthYear=" + birthYear +
                ", homeAddress='" + homeAddress + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", sex='" + sex + '\'' +
                ", interests='" + interests + '\'' +
                ", zodiacSign='" + zodiacSign + '\'' +
                '}';
    }
}
