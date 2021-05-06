package io.github.ititus.pdx.util.io;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class FileNameFilter implements PathFilter {

    private final Predicate<? super String> nameFilter;

    private FileNameFilter(Predicate<? super String> nameFilter) {
        this.nameFilter = nameFilter;
    }

    public static FileNameFilter ofNames(String... names) {
        Set<String> nameSet = Set.of(names);
        return ofPredicate(nameSet::contains);
    }

    public static FileNameFilter ofNames(Collection<? extends String> names) {
        Set<String> nameSet = Set.copyOf(names);
        return ofPredicate(nameSet::contains);
    }

    public static FileNameFilter ofRegex(String nameRegex) {
        return ofRegex(nameRegex, 0);
    }

    public static FileNameFilter ofRegex(String nameRegex, int flags) {
        Pattern p = Pattern.compile(nameRegex, flags);
        return ofPredicate(n -> p.matcher(n).matches());
    }

    public static FileNameFilter ofPredicate(Predicate<? super String> nameFilter) {
        return new FileNameFilter(Objects.requireNonNull(nameFilter));
    }

    @Override
    public boolean test(Path p) {
        return p != null && nameFilter.test(p.getFileName().toString());
    }
}
