import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Organization {

    private Position root;

    public Organization() {
        root = createOrganization();
    }

    protected abstract Position createOrganization();


    /**
     * hire the given person as an employee in the position that has that title
     *
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        List<Position> positions = new ArrayList<>();
        List<Position>positionreports = new ArrayList<>();
        positions.add(root);
        int id = 0;
        //Looping through all of the positions to see if they have anybody that reports to them
        do {
            for (Position p : positions){
                if (!p.getDirectReports().isEmpty()){
                   for (Position pdr : p.getDirectReports()){
       //If the position has people that report to them, and it's not yet on our array list, making a copy to add on to our array list
                       if (!positions.contains(pdr)){
                           positionreports.add(pdr);
                       }
                   }
                }
            }
      // if the position reports are empty then we have captured all potential positions
            if (positionreports.isEmpty()){
                break;
            }
     // adding copies to the original list we are iterating over and clearing out the list to avoid duplicates
            positions.addAll(positionreports);
            positionreports.clear();
        } while (true);
  //once we have all of the positions then we can iterate over them and assign identifiers and return empty if we have iterated through
  // the whole thing and have not found a position with that title;
        for (Position p : positions){
            id++;
            if (p.getTitle().equals(title)){
                Employee employee = new Employee(id, person);
                p.setEmployee(Optional.of(employee));
                return Optional.of(p);
            }
           else if (id == positions.size()){
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }

    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for (Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "\t"));
        }
        return sb.toString();
    }
}
