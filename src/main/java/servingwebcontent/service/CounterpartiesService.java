package servingwebcontent.service;

import org.springframework.stereotype.Service;

@Service
public class CounterpartiesService {
    public boolean tryParseLong(String value) {
        try {
            long result = Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
