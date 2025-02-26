package model.family_tree;
import model.human.HumanComparatorByName;
import model.human.HumanComparatorByAge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FamilyTree<E extends TreeGenerality<E>> implements Serializable, Iterable<E> {
    private int humanId;
    private List<E> humanList;

    public FamilyTree(){
        this(new ArrayList<>());
    }

    public FamilyTree(List<E> humanList) {
        this.humanList = humanList;
    }

    public boolean add(E human){
        if (human == null){
            return false;
        }
        if(!humanList.contains(human)){
            humanList.add(human);
            human.setId(humanId++);
            addToParents(human);
            addToChildren(human);
            return true;
        }

        return false;
    }

    public void addToParents(E human){
        for(E parent: human.getParents()){
            parent.addChild(human);
        }
    }

    public void addToChildren(E human){
        for(E child: human.getChildren()){
            child.addParent(human);
        }
    }

    public  boolean setWedding(int humanId1, int humanId2){
        if(checkId(humanId1) && checkId(humanId2)){
            E human1 = getById(humanId1);
            E human2 = getById(humanId2);
            return setWedding(human1, human2);
        }

        return false;
    }

    public boolean setWedding(E human1, E human2){
        if (human1.getPartner() == null && human2.getPartner() == null){
            human1.setPartner(human2);
            human2.setPartner(human1);
            return true;
        } else {
            return false;
        }
    }

    public boolean setDivorce(int humanId1, int humanId2){
        if(checkId(humanId1) && checkId(humanId2)){
            E human1 = getById(humanId1);
            E human2 = getById(humanId2);
            return setDivorce(human1, human2);
        }
        return false;
    }

    public boolean setDivorce(E human1, E human2){
        if(human1.getPartner() != null && human2.getPartner() != null){
            human1.setPartner(null);
            human2.setPartner(null);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(int humanId){
        if(checkId(humanId)){
            E e = getById(humanId);
            return humanList.remove(e);
        }
        return false;
    }

    private boolean checkId(int id){
        return  id < humanId && id >= 0;
    }

    public E getById(int id){
        for(E human : humanList){
            if(human.getId() == id){
                return human;
            }
        }
        return null;
    }

    public String toString(){
        return getInfo();
    }
    public String getInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("В дереве ");
        sb.append(humanList.size());
        sb.append(" объектов: ");
        sb.append("\n");
        for(E human : humanList){
            sb.append(human);
            sb.append("\n");
        }

        return sb.toString();
    }

    public void sortByName(){
        humanList.sort(new HumanComparatorByName());
    }

    public void sortByAge(){
        humanList.sort(new HumanComparatorByAge());
    }
    @Override
    public Iterator<E> iterator() {
        return new HumanIterator(humanList);
    }


}
