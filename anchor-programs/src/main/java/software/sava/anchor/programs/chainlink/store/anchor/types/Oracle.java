package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Oracle(PublicKey transmitter,
                     SigningKey signer,
                     PublicKey payee,
                     PublicKey proposedPayee,
                     int fromRoundId,
                     long paymentGjuels) implements Borsh {

  public static final int BYTES = 128;

  public static Oracle read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var transmitter = readPubKey(_data, i);
    i += 32;
    final var signer = SigningKey.read(_data, i);
    i += Borsh.len(signer);
    final var payee = readPubKey(_data, i);
    i += 32;
    final var proposedPayee = readPubKey(_data, i);
    i += 32;
    final var fromRoundId = getInt32LE(_data, i);
    i += 4;
    final var paymentGjuels = getInt64LE(_data, i);
    return new Oracle(transmitter,
                      signer,
                      payee,
                      proposedPayee,
                      fromRoundId,
                      paymentGjuels);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    transmitter.write(_data, i);
    i += 32;
    i += Borsh.write(signer, _data, i);
    payee.write(_data, i);
    i += 32;
    proposedPayee.write(_data, i);
    i += 32;
    putInt32LE(_data, i, fromRoundId);
    i += 4;
    putInt64LE(_data, i, paymentGjuels);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
