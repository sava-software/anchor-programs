package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record CreateMasterEditionArgs(OptionalLong maxSupply) implements Borsh {

  public static CreateMasterEditionArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var maxSupply = _data[offset] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, offset + 1));
    return new CreateMasterEditionArgs(maxSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(maxSupply, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (maxSupply == null || maxSupply.isEmpty() ? 1 : (1 + 8));
  }
}
