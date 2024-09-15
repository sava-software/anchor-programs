package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record UpdatePositionOperator(PublicKey position,
                                     PublicKey oldOperator,
                                     PublicKey newOperator) implements Borsh {

  public static final int BYTES = 96;

  public static UpdatePositionOperator read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var position = readPubKey(_data, i);
    i += 32;
    final var oldOperator = readPubKey(_data, i);
    i += 32;
    final var newOperator = readPubKey(_data, i);
    return new UpdatePositionOperator(position, oldOperator, newOperator);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    position.write(_data, i);
    i += 32;
    oldOperator.write(_data, i);
    i += 32;
    newOperator.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
