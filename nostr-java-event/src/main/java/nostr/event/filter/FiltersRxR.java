//package nostr.event.filter;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NonNull;
//import lombok.Setter;
//
//import static java.util.stream.Collectors.groupingBy;
//
//@Getter
//@EqualsAndHashCode
//public class FiltersRxR {
//    public static final int DEFAULT_FILTERS_LIMIT = 10;
//    private final Map<String, List<FilterableRxR>> filtersMap;
//
//    @Setter
//    private Integer limit = DEFAULT_FILTERS_LIMIT;
//
//    public FiltersRxR(@NonNull FilterableRxR... filterablesByDefaultType) {
//        this(List.of(filterablesByDefaultType));
//    }
//
//    public FiltersRxR(@NonNull List<FilterableRxR> filterablesByDefaultType) {
//        this(filterablesByDefaultType.stream().collect(groupingBy(FilterableRxR::getFilterKey)));
//    }
//
//    private FiltersRxR(@NonNull Map<String, List<FilterableRxR>> filterablesByCustomType) {
//        validateFiltersMap(filterablesByCustomType);
//        this.filtersMap = filterablesByCustomType;
//    }
//
//    public List<FilterableRxR> getFilterByType(@NonNull String type) {
//        return Objects.nonNull(filtersMap.get(type)) ?  filtersMap.get(type) : List.of();
//    }
//
//    private static void validateFiltersMap(Map<String, List<FilterableRxR>> filtersMap) throws IllegalArgumentException {
//        if (filtersMap.isEmpty()) {
//            throw new IllegalArgumentException("Filters cannot be empty.");
//        }
//
//        filtersMap.values().forEach(filterables -> {
//            if (filterables.isEmpty()) {
//                throw new IllegalArgumentException("Filters cannot be empty.");
//            }
//        });
//
//        filtersMap.forEach((key, value) -> {
//            if (key.isEmpty())
//                throw new IllegalArgumentException(String.format("Filter key for filterable [%s] is not defined", value.getFirst().getFilterKey()));
//        });
//    }
//}
