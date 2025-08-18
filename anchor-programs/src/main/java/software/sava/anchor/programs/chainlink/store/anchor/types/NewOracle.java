package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record NewOracle(byte[] signer, PublicKey transmitter) implements Borsh {

  public static final int BYTES = 52;
  public static final int SIGNER_LEN = 20;

  public static NewOracle read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var signer = new byte[20];
    i += Borsh.readArray(signer, _data, i);
    final var transmitter = readPubKey(_data, i);
    return new NewOracle(signer, transmitter);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(signer, _data, i);
    transmitter.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
