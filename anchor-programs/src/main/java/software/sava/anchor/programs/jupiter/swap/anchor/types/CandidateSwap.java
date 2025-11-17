package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface CandidateSwap extends RustEnum permits
  CandidateSwap.HumidiFi,
  CandidateSwap.TesseraV {

  static CandidateSwap read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> HumidiFi.read(_data, i);
      case 1 -> TesseraV.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CandidateSwap]", ordinal
      ));
    };
  }

  record HumidiFi(long swapId, boolean isBaseToQuote) implements CandidateSwap {

    public static final int BYTES = 9;

    public static HumidiFi read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var swapId = getInt64LE(_data, i);
      i += 8;
      final var isBaseToQuote = _data[i] == 1;
      return new HumidiFi(swapId, isBaseToQuote);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, swapId);
      i += 8;
      _data[i] = (byte) (isBaseToQuote ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record TesseraV(Side val) implements BorshEnum, CandidateSwap {

    public static TesseraV read(final byte[] _data, final int offset) {
      return new TesseraV(Side.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
