package software.sava.anchor.programs.pyth.receiver.anchor.types;

import software.sava.core.borsh.RustEnum;

// * This enum represents how many guardian signatures were checked for a Pythnet price update
// * If full, guardian quorum has been attained
// * If partial, at least config.minimum signatures have been verified, but in the case config.minimum_signatures changes in the future we also include the number of signatures that were checked
public sealed interface VerificationLevel extends RustEnum permits
  VerificationLevel.Partial,
  VerificationLevel.Full {

  static VerificationLevel read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> Partial.read(_data, i);
      case 1 -> Full.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [VerificationLevel]", ordinal
      ));
    };
  }

  record Partial(int val) implements EnumInt8, VerificationLevel {

    public static Partial read(final byte[] _data, int i) {
      return new Partial(_data[i] & 0xFF);
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Full() implements EnumNone, VerificationLevel {

    public static final Full INSTANCE = new Full();

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
