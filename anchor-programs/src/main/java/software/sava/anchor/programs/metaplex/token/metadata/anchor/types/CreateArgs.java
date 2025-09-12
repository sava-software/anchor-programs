package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.OptionalInt;

import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

public sealed interface CreateArgs extends RustEnum permits
  CreateArgs.V1 {

  static CreateArgs read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CreateArgs]", ordinal
      ));
    };
  }

  record V1(AssetData assetData,
            OptionalInt decimals,
            PrintSupply printSupply) implements CreateArgs {

    public static V1 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var assetData = AssetData.read(_data, i);
      i += Borsh.len(assetData);
      final var decimals = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
      if (decimals.isPresent()) {
        ++i;
      }
      final var printSupply = _data[i++] == 0 ? null : PrintSupply.read(_data, i);
      return new V1(assetData, decimals, printSupply);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.write(assetData, _data, i);
      i += Borsh.writeOptionalbyte(decimals, _data, i);
      i += Borsh.writeOptional(printSupply, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + Borsh.len(assetData) + (decimals == null || decimals.isEmpty() ? 1 : (1 + 1)) + (printSupply == null ? 1 : (1 + Borsh.len(printSupply)));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }
}
