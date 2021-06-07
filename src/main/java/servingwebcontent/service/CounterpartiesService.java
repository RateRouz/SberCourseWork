package servingwebcontent.service;

import org.springframework.stereotype.Service;

@Service
public class CounterpartiesService {
    public boolean tryParseInt(String value) {
        try {
            int result = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
