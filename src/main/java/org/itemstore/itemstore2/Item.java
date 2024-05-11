package org.itemstore.itemstore2;

public class Item {
    protected String name;
    protected String description;
    protected int price;
    private static int numofProducts=0;
    protected int numOfProduct;
    public Item(String name, int price , String description) {
        this.name = name;
        this.description = description;
        this.price = price;
        numofProducts++;
        numOfProduct=numofProducts;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
}
