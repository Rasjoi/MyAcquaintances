package coursework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Acquaintances implements Serializable {
    private ArrayList<Acquaintance> acquaintancesList;

    public Acquaintances() {
        acquaintancesList = new ArrayList<>();
    }
    public ArrayList<Acquaintance> getAcquaintancesList() {
        return acquaintancesList;
    }
    public void setAcquaintancesList(ArrayList<Acquaintance> acquaintancesList){
        this.acquaintancesList = acquaintancesList;
    }

    /**
     * Добавить нового знакомого в список
     * @param acquaintance знакомый
     */
    public void add(Acquaintance acquaintance){
        acquaintancesList.add(acquaintance);
    }

    /**
     * Изменить знакомого в списке по айди
     * @param id порядковый номер знакомого в списке
     * @param acquaintance знакомый
     */
    public void set(int id, Acquaintance acquaintance){
        acquaintancesList.set(id, acquaintance);
    }

    /**
     * Убрать знакомого из списка передав его объект
     * @param acquaintance знакомый
     */
    public void remove(Acquaintance acquaintance){
        acquaintancesList.remove(acquaintance);
    }

    /**
     * Удаление знакомого из списка по порядковому номеру
     * @param id порядковый номер знакомого в списке
     */
    public void remove(int id) throws IllegalArgumentException {
        if (id < acquaintancesList.size() && id >= 0){
            acquaintancesList.remove(id);
        } else {
            throw new IllegalArgumentException("Знакомого по такому индексу нет");
        }
    }

    @Override
    public String toString() {
        return "Acquaintances{" +
                "acquaintancesList=" + acquaintancesList +
                '}';
    }
}
