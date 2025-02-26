package software.sava.anchor.programs.metadao.amm.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record CreateAmmEvent(CommonFields common,
                             BigInteger twapInitialObservation,
                             BigInteger twapMaxObservationChangePerUpdate,
                             PublicKey lpMint,
                             PublicKey baseMint,
                             PublicKey quoteMint,
                             PublicKey vaultAtaBase,
                             PublicKey vaultAtaQuote) implements Borsh {

  public static final int BYTES = 344;

  public static CreateAmmEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var lpMint = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var vaultAtaBase = readPubKey(_data, i);
    i += 32;
    final var vaultAtaQuote = readPubKey(_data, i);
    return new CreateAmmEvent(common,
                              twapInitialObservation,
                              twapMaxObservationChangePerUpdate,
                              lpMint,
                              baseMint,
                              quoteMint,
                              vaultAtaBase,
                              vaultAtaQuote);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    lpMint.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    vaultAtaBase.write(_data, i);
    i += 32;
    vaultAtaQuote.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
