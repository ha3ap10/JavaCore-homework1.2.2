import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        System.out.println("Количество несовершеннолетних " + countOfUnderage(persons));

        List<String> listOfConscripts = conscripts(persons);

        List<String> listOfWorkablePeople = workablePeople(persons);

    }

    static int countOfUnderage(Collection<Person> personsColl) {
        Stream<Person> personsStream = personsColl.stream();
        return (int) personsStream
                .filter(x -> x.getAge() < 18)
                .count();
    }

    static List<String> conscripts(Collection<Person> personsColl) {
        Stream<Person> personsStream = personsColl.stream();
        return personsStream
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() < 27)
                .map(Person::getName)
                .collect(Collectors.toList());
    }

    static List<String> workablePeople(Collection<Person> personsColl) {
        Stream<Person> personsStream = personsColl.stream();
        return personsStream
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getSex() == Sex.MAN && person.getAge() < 65
                        || person.getSex() == Sex.WOMAN && person.getAge() < 60)
//                .sorted(Comparator.comparing(Person::getAge))
//                .sorted(Comparator.comparing(Person::getName))
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::toString)
                .collect(Collectors.toList());
    }
}
