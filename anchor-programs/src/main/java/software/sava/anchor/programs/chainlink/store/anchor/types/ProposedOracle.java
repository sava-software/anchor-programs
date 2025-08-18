package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record ProposedOracle(PublicKey transmitter,
                             SigningKey signer,
                             int padding,
                             PublicKey payee) implements Borsh {

  public static final int BYTES = 88;

  public static ProposedOracle read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var transmitter = readPubKey(_data, i);
    i += 32;
    final var signer = SigningKey.read(_data, i);
    i += Borsh.len(signer);
    final var padding = getInt32LE(_data, i);
    i += 4;
    final var payee = readPubKey(_data, i);
    return new ProposedOracle(transmitter,
                              signer,
                              padding,
                              payee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    transmitter.write(_data, i);
    i += 32;
    i += Borsh.write(signer, _data, i);
    putInt32LE(_data, i, padding);
    i += 4;
    payee.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
