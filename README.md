# autonewline

**autonewline** inserts/removes newlines (and other whitespace) within precise locations of your Clojure forms, according to a set of (configurable) rules.

These substitutions are perfomed using [rewrite-clj](https://github.com/xsc/rewrite-clj), the same library that powers [cljfmt](https://github.com/weavejester/cljfmt) and [zprint](https://github.com/kkinnear/zprint).

**autonewline** is meant to be run right before your main formatter (e.g. cljfmt/zprint): merely newlines are inserted, but that will normally leave misindented code. That job is better left to the mentioned libraries.

## Rationale

If you like a standardized indentation, then standarized placement of newlines is the next logical step.

Specifically, using a carefully thought-out style will result in more expressive code, that also will grow more gracefully (read: less noisy diffs - a typical concern for Lisps).

I would say that a number of Clojure programmers tend to compress a lot of code in a single line.
IMO, just because you can (i.e. it's enabled by the language's concision), it doesn't mean you should.

'Overcompressed' code tends to mix ideas, contexts, in a way that creates unnecessary cognitive effort.
Some compare the human mind to a stack machine. I definitely want my mind to operate at one frame at a time!

## What it looks like

**autonewline** turns this:

```clojure
(case 1 2 3 4 5)
```

into this:

```clojure
(case 1
  2 3
  4 5)
```

...and it turns this:

```clojure
(cond true 1 false 2)
```

into this:

```clojure
(cond
  true
  1
  
  false
  2)
```

Note how the `case` and `cond` groupings are different. That design didn't come out of thin air, but rather, from years of observations/trial/error.

For the given examples:

* `case` tends to have short left-hand exprs
  * therefore a compact formatting is fine.

* `cond` clauses (both left- and right-hand) can grow arbitrarily big
  * therefore the formatting should assume that growth,
  that otherwise would easily break a column limit (e.g. 80), forcing reformatting of unrelated lines, resulting in a noisy diff.

One last example. The following:

```clojure
(fn [x] 1)
```

will be turned into:

```clojure
(fn [x]
  1)
```

Why? Because we are dealing with a macro call, not a function call. When I see `(anything [x] 1)`, I expect `anything` to be a function, not a macro.

Particularly if I see `[x]` in a one-liner, I expect it to be regular data, not code.
Note how `(fn [x])` and `(identity [x])` have vastly different semantics for `[x]`.

Intentful, consistent newline placement makes those differences evident, decreasing cognitive strain.

## License

Copyright © vemv.net

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
