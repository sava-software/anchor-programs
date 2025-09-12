package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface CollectionToggle extends RustEnum permits
  CollectionToggle.None,
  CollectionToggle.Clear,
  CollectionToggle.Set {

  static CollectionToggle read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> None.INSTANCE;
      case 1 -> Clear.INSTANCE;
      case 2 -> Set.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CollectionToggle]", ordinal
      ));
    };
  }

  record None() implements EnumNone, CollectionToggle {

    public static final None INSTANCE = new None();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Clear() implements EnumNone, CollectionToggle {

    public static final Clear INSTANCE = new Clear();

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record Set(Collection val) implements BorshEnum, CollectionToggle {

    public static Set read(final byte[] _data, final int offset) {
      return new Set(Collection.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
