export interface Page<T> {
    content: T[];

    totalPages: number;
    totalElements: number;

    first: boolean;
    last: boolean;
    empty: boolean;

    size: number;
    number: number;
    numberOfElements: number;
}