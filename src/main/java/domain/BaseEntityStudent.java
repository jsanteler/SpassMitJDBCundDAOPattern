package domain;

public class BaseEntityStudent {

    private Long id;

    public BaseEntityStudent(Long id) {
        setId(id);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {

        if(id==null || id >= 0){

            this.id = id;
        }else {
            throw new InvalidValueException("Student-ID muss größer gleich 0 sein!");
        }
    }

    @Override
    public String toString() {
        return "BaseEntityStudent{" +
                "id=" + id +
                '}';
    }
}
