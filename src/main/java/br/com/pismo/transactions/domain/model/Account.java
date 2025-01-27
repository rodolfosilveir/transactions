package br.com.pismo.transactions.domain.model;

import java.util.Random;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Account {

    private Integer id;

    private UUID uuidUser;

    private String accountNumber;

    private String accountDigit;

    private String level;

    private static final Random RANDOM = new Random();

    public Account(){
        this.accountNumber = generateRandomAccountValues(5, 7);
        this.accountDigit = generateRandomAccountValues(1, 1);
        this.level = "SILVER";
    }

    private String generateRandomAccountValues(int minLength, int maxLength){
        int length = RANDOM.nextInt(maxLength - minLength + 1) + minLength;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }

        return sb.toString();
    }
    
}
