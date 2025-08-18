package software.sava.anchor.programs.chainlink.store.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record Proposal(PublicKey _address,
                       Discriminator discriminator,
                       int version,
                       PublicKey owner,
                       int state,
                       int f,
                       int padding0,
                       int padding1,
                       PublicKey tokenMint,
                       ProposedOracles oracles,
                       OffchainConfig offchainConfig) implements Borsh {

  public static final int BYTES = 5872;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VERSION_OFFSET = 8;
  public static final int OWNER_OFFSET = 9;
  public static final int STATE_OFFSET = 41;
  public static final int F_OFFSET = 42;
  public static final int PADDING_0_OFFSET = 43;
  public static final int PADDING_1_OFFSET = 44;
  public static final int TOKEN_MINT_OFFSET = 48;
  public static final int ORACLES_OFFSET = 80;
  public static final int OFFCHAIN_CONFIG_OFFSET = 1760;

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createStateFilter(final int state) {
    return Filter.createMemCompFilter(STATE_OFFSET, new byte[]{(byte) state});
  }

  public static Filter createFFilter(final int f) {
    return Filter.createMemCompFilter(F_OFFSET, new byte[]{(byte) f});
  }

  public static Filter createPadding0Filter(final int padding0) {
    return Filter.createMemCompFilter(PADDING_0_OFFSET, new byte[]{(byte) padding0});
  }

  public static Filter createPadding1Filter(final int padding1) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, padding1);
    return Filter.createMemCompFilter(PADDING_1_OFFSET, _data);
  }

  public static Filter createTokenMintFilter(final PublicKey tokenMint) {
    return Filter.createMemCompFilter(TOKEN_MINT_OFFSET, tokenMint);
  }

  public static Proposal read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Proposal read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Proposal read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Proposal> FACTORY = Proposal::read;

  public static Proposal read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = _data[i] & 0xFF;
    ++i;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var state = _data[i] & 0xFF;
    ++i;
    final var f = _data[i] & 0xFF;
    ++i;
    final var padding0 = _data[i] & 0xFF;
    ++i;
    final var padding1 = getInt32LE(_data, i);
    i += 4;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var oracles = ProposedOracles.read(_data, i);
    i += Borsh.len(oracles);
    final var offchainConfig = OffchainConfig.read(_data, i);
    return new Proposal(_address,
                        discriminator,
                        version,
                        owner,
                        state,
                        f,
                        padding0,
                        padding1,
                        tokenMint,
                        oracles,
                        offchainConfig);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) version;
    ++i;
    owner.write(_data, i);
    i += 32;
    _data[i] = (byte) state;
    ++i;
    _data[i] = (byte) f;
    ++i;
    _data[i] = (byte) padding0;
    ++i;
    putInt32LE(_data, i, padding1);
    i += 4;
    tokenMint.write(_data, i);
    i += 32;
    i += Borsh.write(oracles, _data, i);
    i += Borsh.write(offchainConfig, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
