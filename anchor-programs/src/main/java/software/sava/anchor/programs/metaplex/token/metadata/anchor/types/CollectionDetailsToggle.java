package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface CollectionDetailsToggle extends RustEnum permits
  CollectionDetailsToggle.None,
  CollectionDetailsToggle.Clear,
  CollectionDetailsToggle.Set {

  static CollectionDetailsToggle read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> None.INSTANCE;
      case 1 -> Clear.INSTANCE;
      case 2 -> Set.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CollectionDetailsToggle]", ordinal
      ));
    };
  }

  record None() implements EnumNone, CollectionDetailsToggle {

    public static final None INSTANCE = new None();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Clear() implements EnumNone, CollectionDetailsToggle {

    public static final Clear INSTANCE = new Clear();

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record Set(CollectionDetails val) implements BorshEnum, CollectionDetailsToggle {

    public static Set read(final byte[] _data, final int offset) {
      return new Set(CollectionDetails.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
