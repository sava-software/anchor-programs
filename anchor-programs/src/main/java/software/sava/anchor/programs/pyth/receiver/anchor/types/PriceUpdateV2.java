package software.sava.anchor.programs.pyth.receiver.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PriceUpdateV2(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey writeAuthority,
                            VerificationLevel verificationLevel,
                            PriceFeedMessage priceMessage,
                            long postedSlot) implements Borsh {

  public static final int WRITE_AUTHORITY_OFFSET = 8;
  public static final int VERIFICATION_LEVEL_OFFSET = 40;

  public static Filter createWriteAuthorityFilter(final PublicKey writeAuthority) {
    return Filter.createMemCompFilter(WRITE_AUTHORITY_OFFSET, writeAuthority);
  }

  public static Filter createVerificationLevelFilter(final VerificationLevel verificationLevel) {
    return Filter.createMemCompFilter(VERIFICATION_LEVEL_OFFSET, verificationLevel.write());
  }

  public static PriceUpdateV2 read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PriceUpdateV2 read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PriceUpdateV2 read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PriceUpdateV2> FACTORY = PriceUpdateV2::read;

  public static PriceUpdateV2 read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var writeAuthority = readPubKey(_data, i);
    i += 32;
    final var verificationLevel = VerificationLevel.read(_data, i);
    i += Borsh.len(verificationLevel);
    final var priceMessage = PriceFeedMessage.read(_data, i);
    i += Borsh.len(priceMessage);
    final var postedSlot = getInt64LE(_data, i);
    return new PriceUpdateV2(_address,
                             discriminator,
                             writeAuthority,
                             verificationLevel,
                             priceMessage,
                             postedSlot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    writeAuthority.write(_data, i);
    i += 32;
    i += Borsh.write(verificationLevel, _data, i);
    i += Borsh.write(priceMessage, _data, i);
    putInt64LE(_data, i, postedSlot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + Borsh.len(verificationLevel) + Borsh.len(priceMessage) + 8;
  }
}
