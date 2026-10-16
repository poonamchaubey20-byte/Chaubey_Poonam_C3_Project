# How to make the 3 graded commits

This zip contains the FINAL state of the code (Part 2 solution + Part 3 solution
already combined). Since grading looks at specific commit messages, you need to
push it in 3 steps rather than one — here's exactly how.

## 1. Create the repo
`<LastName>_<FirstName>_C3_Project`, public, then clone it locally and copy
these files in (everything except this instructions file and README section
below).

## Commit 1 — "Part 2: Solution"
Copy in everything AS-IS except leave `Restaurant.java`'s `getOrderValue()`
method, and the "ORDER TOTAL (Part 3)" test block in `RestaurantTest.java`,
OUT for now (delete/comment them out). Everything else — `getMenu()`,
`isRestaurantOpen()`, `findRestaurantByName()`, both refactored test classes —
should be in and passing.

```
git add .
git commit -m "Part 2: Solution"
git push
```

## Commit 2 — "Part 3: Failing test case"
Now add the two order-total test methods to `RestaurantTest.java` (the
"ORDER TOTAL" block), but add `getOrderValue()` to `Restaurant.java` as a
stub that compiles but is obviously wrong, e.g.:

```java
public int getOrderValue(String itemNames) {
    return 0; // TODO: implement
}
```

Run `mvn test` — the two order-total tests should now FAIL (compiles, but
wrong answer). That's the point of this commit.

```
git add .
git commit -m "Part 3: Failing test case"
git push
```

## Commit 3 — "Part 3: Solution"
Replace the stub with the real implementation (already written for you in
the `Restaurant.java` included in this zip — the version that splits on
comma, looks up each item, and sums the prices). Run `mvn test` again —
everything should pass now.

```
git add .
git commit -m "Part 3: Solution"
git push
```

Only the LATEST commit with each exact message is used for grading, so it's
fine to keep committing/pushing after this if you spot something to fix —
just make sure the final commit carrying each message is the one you want
graded.
